package vn.compedia.api.service;

import com.google.common.collect.Maps;
import com.google.maps.errors.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.dto.ProductDto;
import vn.compedia.api.dto.ProductLangChangeDto;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.entity.Product;
import vn.compedia.api.entity.ProductImages;
import vn.compedia.api.entity.ProductLang;
import vn.compedia.api.entity.ProductSize;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.repository.*;
import vn.compedia.api.response.product.ProductResponse;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.user.UserContextHolder;
import vn.compedia.api.utility.FileUtil;
import java.io.IOException;
import java.util.*;

@Log4j2
@Service
public class ProductService {
    @Value("${vn.compedia.static.context}")
    private String staticContext;
    @Value("${accept_image_file_types}")
    private String acceptImageTypes;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImagesRepository productImagesRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductLangRepository productLangRepository;

    @Transactional
    public void createOrUpdateProduct(ProductDto productDto, MultipartFile file, MultipartFile[] files, Integer languageId) throws VietTienException, VietTienInvalidParamsException {
        productDto = validate(productDto);
        Map<String, String> errors = Maps.newHashMap();
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);

//        List<Shop> ListShopfindId = shopRepository.findLitsShopByAccountId(UserContextHolder.getUser().getAccountId());
//        if (ListShopfindId.size() == 0) {
//            product.setShopId(0L);
//        } else if (ListShopfindId.size() > 1) {
//            throw new VietTienException(MessageUtil.ACCOUNT_NOT_MANY_SHOPS);
//        } else {
//            Shop shopFindId = shopRepository.findShopByAccountId(UserContextHolder.getUser().getAccountId());
//            product.setShopId(shopFindId.getShopId());
//        }
//
//        product.setImages(file != null && !file.isEmpty() && FileUtil.isAcceptFileTypeImage(file.getOriginalFilename())
//                ? FileUtil.saveImage(productDto.getImageFile()) : product.getImages());
//
//        if (productDto.getQuantity_in_stock() == null) {
//            productDto.setQuantity_in_stock(0L);
//        }

        if (productDto.getProductId() == null) {
            List<Product> listCheckCode = productRepository.findAllProductByCodeAndStatus(productDto.getCode());
            if (listCheckCode.size() > 0) {
                throw new VietTienException(MessageUtil.CODE_PRODUCT_DUPLICATE);
            }
            product.setCreateBy(UserContextHolder.getUser().getAccountId());
            product.setCreateDate(new Date());
            product.setUpdateBy(UserContextHolder.getUser().getAccountId());
            product.setUpdateDate(new Date());
        } else {
            product = productRepository.findByProductId(productDto.getProductId());
            if (product == null) {
                throw new VietTienException("Sản phẩm không tồn tại");
            }
            BeanUtils.copyProperties(productDto, product);
            product.setUpdateBy(UserContextHolder.getUser().getAccountId());
            product.setUpdateDate(new Date());
        }
        product.setStatus(DbConstant.PRODUCT_ACTIVE);
        productRepository.save(product);

        if (productDto.getProductId() != null) {
            ProductLang productLang = productLangRepository.findAllByProductIdAndLanguageId(product.getProductId(), languageId);
            if (productLang != null) {
                productLangRepository.delete(productLang);
            }
        }

        if (productDto.getProductLangList() != null) {
            for (ProductLang productLang : productDto.getProductLangList()) {
                productLang.setProductId(product.getProductId());
                productLang.setLanguageId(languageId);
                productLangRepository.save(productLang);
            }
        }
        if (productDto.getProductId() != null) {
            List<ProductSize> productSizeList = productSizeRepository.findAllByProductId(productDto.getProductId());
            productSizeRepository.deleteAll(productSizeList);
        }

        if (productDto.getListProductSize() != null) {
            List<ProductSize> productSizeList = productDto.getListProductSize();
            for (ProductSize productSize : productSizeList) {
                productSize.setProductId(product.getProductId());
            }

            productSizeRepository.saveAll(productSizeList);
        }

        // s_product_image
        if (files != null && files.length > 0) {
            for (MultipartFile multipartFile : files) {
                if (!multipartFile.isEmpty() && !FileUtil.isAcceptFileTypeImage(multipartFile.getOriginalFilename())) {
                    errors.put("files", String.format("File bạn chọn (%s) không đúng định dạng, chỉ cho phép : %s ",
                            FileUtil.getFileExtFromFileName(Objects.requireNonNull(multipartFile.getOriginalFilename())),
                            acceptImageTypes.replace(",", ", ")));
                    break;
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        List<ProductImages> productImagesList = productImagesRepository.findAllByProductId(product.getProductId());
        productImagesRepository.deleteAll(productImagesList);
        Map<String, String> fileList = new LinkedHashMap<>();
        List<ProductImages> sProductImages = new ArrayList<>();
        if (files != null && files.length > 0) {
            fileList.putAll(FileUtil.saveFiles(files));
            for (String key : fileList.keySet()) {
                ProductImages sProductImage = new ProductImages(null, product.getProductId(), key);
                sProductImages.add(sProductImage);
            }
            productImagesRepository.saveAll(sProductImages);
        }

    }

    public VietTienPageDto<?> search(Map<String, Object> filters, int page, int pageSize, int languageId) throws VietTienInvalidParamsException, IOException, InterruptedException, ApiException {
        Map<String, String> errors = Maps.newHashMap();
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        Map<String, String> sorts = new HashMap<>();
        if (filters.get("sorts") != null) {
            sorts.putAll((Map<String, String>) filters.get("sorts"));
        }
        Page<ProductResponse> showPage = productRepository.search(filters, sorts, PageRequest.of(page - 1, pageSize), languageId);
        return VietTienPageDto.build(showPage);
    }

    public ProductResponse getProductById(Long id, Integer languageId) throws VietTienNotFoundException, VietTienInvalidParamsException {
        ProductResponse productResponse = new ProductResponse();
        Product product = productRepository.findById(id).orElseThrow(() -> new VietTienNotFoundException("Not found id: " + id));
        Map<String, String> errors = Maps.newHashMap();
        if (product.getStatus().equals(DbConstant.PRODUCT_NOT_ACTIVE)) {
            errors.put("status", MessageUtil.PRODUCT_HAS_BEEN_DELETED);
        }
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        BeanUtils.copyProperties(product, productResponse);
        List<ProductSize> productSizes = productSizeRepository.findAllByProductId(product.getProductId());
        productResponse.setListProductSize(productSizes);
        List<ProductImages> productImagesList = productImagesRepository.findAllByProductId(product.getProductId());
        productResponse.setListProductImages(productImagesList);
        ProductLang productLang = productLangRepository.findAllByProductIdAndLanguageId(id, languageId);
        productResponse.setProductLang(productLang);
        return productResponse;
    }

    public ProductDto validate(ProductDto productDto) throws VietTienInvalidParamsException {
        Map<String, String> errors = Maps.newHashMap();

        if (StringUtils.isNotBlank(productDto.getCode())) {
            if (productDto.getCode().trim().length() > 15) {
                errors.put("code", MessageUtil.CODE_PRODUCT_HAS_MAX_LENGTH);
            } else {
                productDto.setCode(productDto.getCode().trim());
            }
        }
//        if (productDto.getObject() == null) {
//            errors.put("object", MessageUtil.OBJECT_PRODUCT_NULL);
//        }
        if (productDto.getProductCategoryId() == null) {
            errors.put("product_category_id", MessageUtil.CATEGORY_PRODUCT_NULL);
        }
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        return productDto;
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteProduct(Long id) throws VietTienException {
        Product product = productRepository.findByProductId(id);
        if (product == null) {
            throw new VietTienException(MessageUtil.PRODUCT_DOES_NOT_EXIST);
        }

        product.setStatus(DbConstant.PRODUCT_NOT_ACTIVE);

        product.setUpdateDate(new Date());
        product.setUpdateBy(UserContextHolder.getUser().getAccountId());
        productRepository.save(product);
    }

    @org.springframework.transaction.annotation.Transactional
    public void changeLanguage(ProductLangChangeDto productLangChangeDto) throws VietTienException {
        if (productLangChangeDto.getProductLangId() != null) {
            ProductLang productLang = productLangRepository.findAllByProductLangId(productLangChangeDto.getProductLangId());
            productLang.setName(productLangChangeDto.getName());
            productLang.setDescription(productLangChangeDto.getDescription());
            productLang.setWholesalePrices(productLangChangeDto.getWholesalePrices());
            productLang.setRetailPrice(productLangChangeDto.getRetailPrice());
            productLang.setLanguageId(productLangChangeDto.getLanguageId());
            productLangRepository.save(productLang);
        } else {
            ProductLang productLang = new ProductLang();
            productLang.setName(productLangChangeDto.getName());
            productLang.setDescription(productLangChangeDto.getDescription());
            productLang.setWholesalePrices(productLangChangeDto.getWholesalePrices());
            productLang.setRetailPrice(productLangChangeDto.getRetailPrice());
            productLang.setLanguageId(productLangChangeDto.getLanguageId());
            productLangRepository.save(productLang);
        }
        Product product = new Product();
        product = productRepository.findByProductId(productLangChangeDto.getProductId());
        product.setUpdateDate(new Date());
        product.setUpdateBy(UserContextHolder.getUser().getAccountId());
        productRepository.save(product);
    }

}
