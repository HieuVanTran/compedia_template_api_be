package vn.compedia.api.service;

import com.google.common.collect.Maps;
import com.google.maps.errors.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.entity.Customer;
import vn.compedia.api.entity.CustomerRole;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.repository.CommuneRepository;
import vn.compedia.api.repository.DistrictRepository;
import vn.compedia.api.repository.ProvinceRepository;
import vn.compedia.api.repository.customer.CustomerRepository;
import vn.compedia.api.repository.customer.CustomerRoleRepository;
import vn.compedia.api.request.ShopInforRequest;
import vn.compedia.api.response.ShopManage.ShopDetailResponse;
import vn.compedia.api.response.ShopManage.ShopListResponse;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.user.UserContextHolder;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopManagementService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerRoleRepository customerRoleRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    @Value("${vn.compedia.static.context}")
    private String staticContext;
    @Value("${accept_image_file_types}")
    private String acceptImageTypes;
    @Value("${validate.phone_number.regex}")
    private String phoneNumberRegex;

    public List<CustomerRole> getCustomerRole() {
        return customerRoleRepository.getCustomerRole();
    }

    public VietTienPageDto<?> search(Map<String, Object> filters, int page, int pageSize) throws VietTienInvalidParamsException, IOException, InterruptedException, ApiException {
        Map<String, String> errors = Maps.newHashMap();
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        Map<String, String> sorts = new HashMap<>();
        if (filters.get("sorts") != null) {
            sorts.putAll((Map<String, String>) filters.get("sorts"));
        }
        Page<ShopListResponse> showPage = customerRepository.search(filters, sorts, PageRequest.of(page - 1, pageSize));
        return VietTienPageDto.build(showPage);
    }

    @Transactional
    public void createOrUpdate(ShopInforRequest shopInforRequest) throws VietTienInvalidParamsException, VietTienException {
        shopInforRequest = validate(shopInforRequest);
        Customer shop = new Customer();
        BeanUtils.copyProperties(shopInforRequest, shop);
        if (shopInforRequest.getCustomerId() == null) {
            shop.setStatus(DbConstant.STATUS_ACTIVE);
            shop.setCreateDate(new Date());
            shop.setCreateBy(UserContextHolder.getUser().getAccountId());
        } else {
            Customer shopBak = customerRepository.findCustomerByCustomerId(shopInforRequest.getCustomerId());
            if (shopBak == null) {
                throw new VietTienException(MessageUtil.SHOP_DOES_NOT_EXIST);
            }
            shop.setCreateBy(shopBak.getCreateBy());
            shop.setCreateDate(shopBak.getCreateDate());
        }
        shop.setUpdateDate(new Date());
        shop.setUpdateBy(UserContextHolder.getUser().getAccountId());
        customerRepository.save(shop);
    }

    public ShopInforRequest validate(ShopInforRequest shopInforRequest) throws VietTienInvalidParamsException {
        Map<String, String> errors = Maps.newHashMap();

        if (StringUtils.isBlank(shopInforRequest.getName().trim())) {
            errors.put("name", MessageUtil.FULL_NAME_NULL);
        } else if (shopInforRequest.getName().trim().length() > 50) {
            errors.put("name", MessageUtil.FULL_NAME_HAS_MAX_LENGTH);
        }

        if (shopInforRequest.getCustomerRoleId() == null) {
            errors.put("name", MessageUtil.FULL_NAME_HAS_MAX_LENGTH);
        }

        if (StringUtils.isBlank(shopInforRequest.getPhone().trim())) {
            errors.put("phone", MessageUtil.PHONE_NULL);
        } else if (!shopInforRequest.getPhone().trim().matches(phoneNumberRegex)) {
            errors.put("phone", MessageUtil.PHONE_INCORRECT_FORMAT);
        } else if (shopInforRequest.getPhone().trim().length() > 50) {
            errors.put("phone", MessageUtil.PHONE_HAS_MAX_LENGTH);
        }

        if (StringUtils.isBlank(shopInforRequest.getEmail().trim())) {
            errors.put("email", MessageUtil.EMAIL_ADDRESS_IS_NOT_ENTERED);
        } else if (!EmailValidator.getInstance().isValid(shopInforRequest.getEmail().trim())) {
            errors.put("email", MessageUtil.EMAIL_INCORRECT_FORMAT);
        } else if (shopInforRequest.getEmail().trim().length() > 50) {
            errors.put("email", MessageUtil.EMAIL_HAS_MAX_LENGTH);
        }

        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }

        shopInforRequest.setName(shopInforRequest.getName().trim());
        shopInforRequest.setEmail(shopInforRequest.getEmail().trim());
        shopInforRequest.setPhone(shopInforRequest.getPhone().trim());
        shopInforRequest.setFacebook(shopInforRequest.getFacebook().trim());
        shopInforRequest.setDescription(shopInforRequest.getDescription().trim());
        shopInforRequest.setAddress(shopInforRequest.getAddress().trim());
        return shopInforRequest;
    }

    public ShopDetailResponse getDetailShop(Long shopId) throws VietTienException {
        Customer shop = customerRepository.findCustomerByCustomerId(shopId);
        if (shop == null) {
            throw new VietTienException(MessageUtil.SHOP_DOES_NOT_EXIST);
        }

        ShopDetailResponse shopDetailResponse = new ShopDetailResponse();
        BeanUtils.copyProperties(shop, shopDetailResponse);
        if (shop.getProvinceId() != null) {
            shopDetailResponse.setProvinceName(provinceRepository.finNameById(shop.getProvinceId()));
        }
        if (shop.getDistrictId() != null) {
            shopDetailResponse.setDistrictName(districtRepository.findNameById(shop.getDistrictId()));
        }
        if (shop.getCommuneId() != null) {
            shopDetailResponse.setCommuneName(communeRepository.findNameById(shop.getCommuneId()));
        }
        if (shop.getCustomerRoleId() != null) {
            shopDetailResponse.setDiscount(customerRoleRepository.findNameById(shop.getCustomerRoleId()));
        }
        return shopDetailResponse;
    }

    public void deleteShop(Long shopId) throws VietTienException {
        Customer shop = customerRepository.findCustomerByCustomerId(shopId);
        if (shop == null) {
            throw new VietTienException(MessageUtil.SHOP_DOES_NOT_EXIST);
        }
        shop.setStatus(DbConstant.STATUS_DELETED);
        customerRepository.save(shop);
    }

}
