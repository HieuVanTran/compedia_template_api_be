package vn.compedia.api.controller;

import com.google.maps.errors.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.request.ShopInforRequest;
import vn.compedia.api.service.ShopManagementService;
import vn.compedia.api.util.MessageUtil;

import java.io.IOException;
import java.util.Map;

@Api(tags = "Shop_Management")
@RestController
@RequestMapping("/api/v1/shop_management")
@Validated
public class ShopManagementController {
    @Autowired
    private ShopManagementService shopManagementService;

    @GetMapping("get_customer_role")
    @ApiOperation(value = "Get all customer role")
    public ResponseEntity<?> getCustomerRole() {
        return VietTienResponseDto.ok(shopManagementService.getCustomerRole(), MessageUtil.GET_ALL_SHOP);
    }

    @PostMapping("get_shops")
    @ApiOperation(value = "Get all shop")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> filters,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "25") int pageSize) throws VietTienInvalidParamsException, InterruptedException, IOException, ApiException {
        return VietTienResponseDto.ok(shopManagementService.search(filters, page, pageSize), MessageUtil.GET_ALL_SHOP);
    }

    @GetMapping("get_shop_detail")
    public ResponseEntity<?> getShopDetail(@RequestParam Long shopId) throws VietTienException {
        return VietTienResponseDto.ok(shopManagementService.getDetailShop(shopId), MessageUtil.GET_ID_SHOP);
    }

    @PostMapping(value = "create_shop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createShop(@RequestBody ShopInforRequest sSupplierRequest) throws VietTienInvalidParamsException, VietTienException {
        shopManagementService.createOrUpdate(sSupplierRequest);
        return VietTienResponseDto.ok(sSupplierRequest, MessageUtil.SAVE_SHOP);
    }

    @PostMapping(value = "edit_shop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> editShop(@RequestBody ShopInforRequest sSupplierRequest) throws VietTienInvalidParamsException, VietTienException {
        shopManagementService.createOrUpdate(sSupplierRequest);
        return VietTienResponseDto.ok(sSupplierRequest, MessageUtil.SAVE_SHOP);
    }

    @GetMapping("delete_shop")
    public ResponseEntity<?> deleteShop(@RequestParam Long shopId) throws VietTienException {
        shopManagementService.deleteShop(shopId);
        return VietTienResponseDto.ok(null, MessageUtil.DELETE_SHOP);
    }
}
