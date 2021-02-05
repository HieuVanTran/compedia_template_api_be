package vn.compedia.api.controller;

import com.google.maps.errors.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.dto.ProductDto;
import vn.compedia.api.dto.ProductLangChangeDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.service.ProductService;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

@Api(tags = "Product")
@RestController
@RequestMapping("/api/v1/product")
@Validated
public class ProductController extends GlobalExceptionHandler {

    @Autowired
    private ProductService productService;

    @PostMapping("get_product")
    @ApiOperation(value = "Get all product")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> filters,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
                                    @RequestParam(value = "language_id") Integer languageId) throws VietTienInvalidParamsException, InterruptedException, IOException, ApiException {
        return VietTienResponseDto.ok(productService.search(filters, page, pageSize, languageId), MessageUtil.GET_ALL_SHOP);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get product by id")
    public ResponseEntity<?> getProduct(@PathVariable Long id, @RequestParam Integer language_id) throws VietTienNotFoundException, VietTienInvalidParamsException {
        return VietTienResponseDto.ok(productService.getProductById(id, language_id), MessageUtil.GET_ID_SHOP);
    }

    @PostMapping("search_product")
    public ResponseEntity<?> searchProduct(@RequestBody Map<String, Object> filters,
                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
                                           @RequestParam(value = "language_id") Integer languageId) throws VietTienInvalidParamsException, InterruptedException, ApiException, IOException {
        return VietTienResponseDto.ok(productService.search(filters, page, pageSize, languageId), MessageUtil.PRODUCT_GET_ALL);
    }

    @PostMapping("change_language")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Change language")
    public ResponseEntity<?> createCategory(
            @RequestParam(name = "product_lang_id", required = false) Long productLangId,
            @RequestParam(name = "product_id", required = false) Long productId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "wholesale_prices", required = false) Double wholesalePrices,
            @RequestParam(name = "retail_price", required = false) Double retailPrice,
            @RequestParam(name = "language_id") Integer laguageId
    ) throws VietTienException, VietTienInvalidParamsException {
        ProductLangChangeDto productLangChangeDto = new ProductLangChangeDto(productLangId, productId, name, description, wholesalePrices, retailPrice, laguageId);
        productService.changeLanguage(productLangChangeDto);
        return VietTienResponseDto.ok(null, MessageUtil.SAVE_SHOP);
    }

    @PostMapping(value = "create_product", consumes = {"multipart/form-data"})
    @ApiOperation("create product")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestPart(value = "json") String json, @RequestParam(value = "files") MultipartFile file, @RequestPart MultipartFile[] files, @RequestParam(value = "language_id") Integer languageId) throws VietTienException, VietTienInvalidParamsException {
        ProductDto productDto = (ProductDto) StringUtil.jsonToObject(json, ProductDto.class);
        if (file != null) {
            productDto.setImageFile(file);
        }
        productService.createOrUpdateProduct(productDto, file, files, languageId);
        return VietTienResponseDto.ok(productDto, MessageUtil.SAVE_PRODUCT);
    }

    @PostMapping(value = "update_product", consumes = {"multipart/form-data"})
    @ApiOperation("update product")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> updateProduct(@RequestPart(value = "json") String json, @RequestParam(value = "files") MultipartFile file, @RequestPart MultipartFile[] files, @RequestParam(value = "language_id") Integer languageId) throws VietTienException, VietTienInvalidParamsException {
        ProductDto productDto = (ProductDto) StringUtil.jsonToObject(json, ProductDto.class);
        if (file != null) {
            productDto.setImageFile(file);
        }
        productService.createOrUpdateProduct(productDto, file, files, languageId);
        return VietTienResponseDto.ok(productDto, MessageUtil.SAVE_PRODUCT);
    }

    @PostMapping("/delete")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Delete product")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "product_id") Long productId) throws VietTienException {
        productService.deleteProduct(productId);
        return VietTienResponseDto.ok(null, MessageUtil.DELETE_PRODUCT);
    }

}
