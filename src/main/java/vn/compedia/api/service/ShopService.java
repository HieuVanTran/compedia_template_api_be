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
import vn.compedia.api.config.Constant;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.Shop;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.repository.*;
import vn.compedia.api.request.AccountRequest;
import vn.compedia.api.request.ShopRequest;
import vn.compedia.api.response.shop.ShopResponse;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;
import vn.compedia.api.utility.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class ShopService {
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
    private CommuneRepository communeRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    public ShopResponse getShopById(Long id) throws VietTienNotFoundException, VietTienInvalidParamsException {
        ShopResponse shopResponse = new ShopResponse();
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new VietTienNotFoundException("Not found id: " + id));

        Map<String, String> errors = Maps.newHashMap();
        if (shop.getStatus().equals(DbConstant.SHOP_INACTIVE)) {
            errors.put("status", MessageUtil.SHOP_HAS_BEEN_DELETED);
        }
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }

        shopResponse.setShopId(shop.getShopId());
        shopResponse.setShopName(shop.getName());
        shopResponse.setShopPhone(shop.getPhone());
        shopResponse.setShopEmail(shop.getEmail());
        shopResponse.setShopFacebook(shop.getFacebook());
        shopResponse.setShopLogo((StringUtil.buildURI(staticContext, shop.getLogo())));
        shopResponse.setShopDescription(shop.getDescription());
        shopResponse.setShopProvinceId(shop.getProvinceId());
        shopResponse.setShopDistrictId(shop.getDistrictId());
        shopResponse.setShopCommuneId(shop.getProvinceId());
        if (shop.getProvinceId() != null) {
            shopResponse.setShopProvinceName(provinceRepository.finNameById(shopResponse.getShopProvinceId()));
        }
        if (shop.getDistrictId() != null) {
            shopResponse.setShopDistrictName(districtRepository.findNameById(shopResponse.getShopDistrictId()));
        }
        if (shop.getCommuneId() != null) {
            shopResponse.setShopCommuneName(communeRepository.findNameById(shopResponse.getShopCommuneId()));
        }
        shopResponse.setShopAddress(shop.getAddress());

        Account account = accountRepository.findById(shop.getAccountId()).orElseThrow(() -> new VietTienNotFoundException("Not found id: " + id));

        shopResponse.setAccountFullName(account.getFullName());
        shopResponse.setAccountPhone(account.getPhone());
        shopResponse.setAccountEmail(account.getEmail());

        return shopResponse;
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteShop(Long id) throws VietTienException {
        Shop shop = shopRepository.findShopByShopId(id);
        if (shop == null) {
            throw new VietTienException(MessageUtil.SHOP_DOES_NOT_EXIST);
        }
        Account account = accountRepository.findAccountByAccountId(shop.getAccountId());
        if (account == null) {
            throw new VietTienException(MessageUtil.ACCOUNT_DOES_NOT_EXIST);
        }
        account.setStatus(DbConstant.ACCOUNT_IN_ACTIVE);
        shop.setStatus(DbConstant.SHOP_INACTIVE);
        account.setUpdateDate(new Date());
        account.setUpdateBy(UserContextHolder.getUser().getAccountId());
        accountRepository.save(account);
        shop.setUpdateDate(new Date());
        shop.setUpdateBy(UserContextHolder.getUser().getAccountId());
        shopRepository.save(shop);
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
        Page<ShopResponse> showPage = shopRepository.search(filters, sorts, PageRequest.of(page - 1, pageSize));
        return VietTienPageDto.build(showPage);
    }

    @Transactional
    public void createShop(AccountRequest accountRequest, ShopRequest shopRequest) throws VietTienException, VietTienInvalidParamsException {
        validateAddShop(accountRequest, shopRequest);
        if (shopRequest.getShopId() == null || shopRequest.getShopId() == 0) {
            Account account = new Account();
            account.setFullName(accountRequest.getFullName());
            account.setPhone(accountRequest.getPhone());
            // account.setDob(accountRequest.getDob());
            account.setEmail(accountRequest.getEmail());

            account.setStatus(DbConstant.ACCOUNT_ACTIVE);
            String newSalt = StringUtil.generateSalt();
            account.setSalt(newSalt);
            account.setPassword(StringUtil.encryptPassword(account.getPassword(), newSalt));
            Date now = new Date();
            if (shopRequest.getShopId() == null) {
                account.setCreateDate(now);
                account.setCreateBy(UserContextHolder.getUser().getAccountId());
            } else {
                account.setUpdateBy(UserContextHolder.getUser().getAccountId());
                account.setUpdateDate(now);
            }
            account.setRoleId(Constant.ROLE_SHOP);
            accountRepository.save(account);

            Account ac = new Account();
            ac = accountRepository.findByEmail(account.getEmail());
            Shop shop = new Shop();
            shop.setAccountId(ac.getAccountId());
            BeanUtils.copyProperties(shopRequest, shop);
            shop.setStatus(DbConstant.SHOP_ACTIVE);
            if (shopRequest.getLogo() != null) {
                shop.setLogo(FileUtil.saveFile(shopRequest.getLogo()));
            }
            shop.setCreateDate(now);
            shop.setCreateBy(UserContextHolder.getUser().getAccountId());
            shopRepository.save(shop);

        } else {
            Account account = new Account();
            Shop shop = new Shop();
            shop = shopRepository.findShopByShopId(shopRequest.getShopId());
            account = accountRepository.findAccountByAccountId(shop.getAccountId());

            List<Account> listCheckPhone = accountRepository.findAccountByPhone(account.getPhone());
            if (listCheckPhone.size() > 1) {
                throw new VietTienException(MessageUtil.EMAIL_ALREADY_EXIST);
            }
            account.setFullName(accountRequest.getFullName());
            account.setPhone(accountRequest.getPhone());
            //  account.setDob(accountRequest.getDob());
            account.setEmail(accountRequest.getEmail());

            account.setUpdateDate(new Date());
            account.setUpdateBy(UserContextHolder.getUser().getAccountId());
            accountRepository.save(account);
            BeanUtils.copyProperties(shopRequest, shop);
            if (shopRequest.getLogo() != null) {
                shop.setLogo(FileUtil.saveFile(shopRequest.getLogo()));
            }
            shop.setUpdateDate(new Date());
            shop.setUpdateBy(UserContextHolder.getUser().getAccountId());
            shopRepository.save(shop);
        }

    }

    private void validateAddShop(AccountRequest accountRequest, ShopRequest shopRequest) throws VietTienInvalidParamsException {
        Map<String, String> errors = Maps.newHashMap();
        if (StringUtils.isBlank(accountRequest.getFullName().trim())) {
            errors.put("full_name", MessageUtil.FULL_NAME_NULL);
        }
        if (accountRequest.getFullName().trim().length() > 50) {
            errors.put("full_name", MessageUtil.FULL_NAME_HAS_MAX_LENGTH);
        }
        if (StringUtils.isBlank(accountRequest.getPhone().trim())) {
            errors.put("phone", MessageUtil.PHONE_NULL);
        }
        if (accountRequest.getPhone().trim().length() > 50) {
            errors.put("phone", MessageUtil.PHONE_HAS_MAX_LENGTH);
        }
        if (StringUtils.isBlank(accountRequest.getEmail().trim())) {
            errors.put("email", MessageUtil.EMAIL_ADDRESS_IS_NOT_ENTERED);
        }
        if (accountRequest.getEmail().trim().length() > 50) {
            errors.put("email", MessageUtil.EMAIL_HAS_MAX_LENGTH);
        }
        if (StringUtils.isBlank(accountRequest.getPassword().trim())) {
            errors.put("password", MessageUtil.PASSWORD_NULL);
        }
        if (StringUtils.isBlank(shopRequest.getName().trim())) {
            errors.put("name", MessageUtil.NAME_SHOP_NULL);
        }
        if (shopRequest.getName().trim().length() > 50) {
            errors.put("name", MessageUtil.NAME_HAS_MAX_LENGTH);
        }
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }

    }

}
