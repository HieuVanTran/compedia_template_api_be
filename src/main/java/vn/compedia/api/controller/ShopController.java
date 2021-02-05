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
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.request.AccountRequest;
import vn.compedia.api.request.ShopRequest;
import vn.compedia.api.service.AccountService;
import vn.compedia.api.service.ShopService;
import vn.compedia.api.util.MessageUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Api(tags = "Shop")
@RestController
@RequestMapping("/api/v1/shop")
@Validated
public class ShopController extends GlobalExceptionHandler {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ShopService shopService;

    @PostMapping("get_shop")
    @ApiOperation(value = "Get all shop")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> filters,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "25") int pageSize) throws VietTienInvalidParamsException, InterruptedException, IOException, ApiException {
        return VietTienResponseDto.ok(shopService.search(filters, page, pageSize), MessageUtil.GET_ALL_SHOP);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get employee by id")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) throws VietTienNotFoundException, VietTienInvalidParamsException {
        return VietTienResponseDto.ok(shopService.getShopById(id), MessageUtil.GET_ID_SHOP);
    }

    @PostMapping("add")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Create or edit shop")
    public ResponseEntity<?> createCategory(
            @RequestParam(name = "shop_id", required = false) Long shopId,
            @RequestParam(name = "shop_name") String shopName,
            @RequestParam(name = "shop_phone", required = false) String shopPhone,
            @RequestParam(name = "shop_email", required = false) String shopEmail,
            @RequestParam(name = "shop_facebook", required = false) String shopFacebook,
            @RequestParam(name = "shop_logo", required = false) MultipartFile shopMultipartFile,
            @RequestParam(name = "shop_description", required = false) String shopDescription,
            @RequestParam(name = "shop_province_id", required = false) Long shopProvinceId,
            @RequestParam(name = "shop_district_id", required = false) Long shopDistrict,
            @RequestParam(name = "shop_commune_id", required = false) Long shopCommuneId,
            @RequestParam(name = "shop_address", required = false) String shopAddress,
            @RequestParam(name = "account_fullName") String accountFullName,
            @RequestParam(name = "account_phone") String accountPhone,
            @RequestParam(name = "account_dob", required = false) Date accountDob,
            @RequestParam(name = "account_gender", required = false) Integer accountGender,
            @RequestParam(name = "account_email") String accountEmail,
            @RequestParam(name = "account_province_id", required = false) Long accountProvinceId,
            @RequestParam(name = "account_district_id", required = false) Long accountDistrictId,
            @RequestParam(name = "account_commune_id", required = false) Long accountCommuneId,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "account_images", required = false) MultipartFile accountMultipartFile,
            @RequestParam(name = "account_password") String accountPassword
    ) throws VietTienException, VietTienInvalidParamsException {
        AccountRequest accountRequest = new AccountRequest(accountFullName, accountPhone, accountDob, accountGender, accountEmail, accountProvinceId, accountDistrictId, accountCommuneId, address, accountMultipartFile, accountPassword);
        ShopRequest shopRequest = new ShopRequest(shopId, shopName, shopPhone, shopEmail, shopFacebook, shopMultipartFile, shopDescription, shopProvinceId, shopDistrict, shopCommuneId, shopAddress);
        shopService.createShop(accountRequest, shopRequest);
        return VietTienResponseDto.ok(null, MessageUtil.SAVE_SHOP);
    }

    @PostMapping("/delete")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Delete shop")
    public ResponseEntity<?> deleteEmployee(@RequestParam(name = "shop_id") Long sEmployeeId) throws VietTienException {
        shopService.deleteShop(sEmployeeId);
        return VietTienResponseDto.ok(null, MessageUtil.DELETE_SHOP);
    }

}
