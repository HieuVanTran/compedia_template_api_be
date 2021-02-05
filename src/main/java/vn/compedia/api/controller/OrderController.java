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
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.request.OrderRequest;
import vn.compedia.api.service.OrderService;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.StringUtil;

import java.io.IOException;
import java.util.Map;

@Api(tags = "OrderShop")
@RestController
@RequestMapping("/api/v1/order")
@Validated
public class OrderController extends GlobalExceptionHandler {

    @Autowired
    private OrderService orderService;

    @PostMapping("get_order")
    @ApiOperation(value = "Get all order")
    public ResponseEntity<?> search(@RequestBody Map<String, Object> filters,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "25") int pageSize) throws VietTienInvalidParamsException, InterruptedException, IOException, ApiException, IOException, ApiException {
        return VietTienResponseDto.ok(orderService.search(filters, page, pageSize), MessageUtil.ORDER_GET_ALL);
    }

//    @GetMapping("/{id}")
//    @ApiOperation("Get order by id")
//    public ResponseEntity<?> getOrderRetail(@PathVariable Long id) throws VietTienNotFoundException, VietTienInvalidParamsException {
//        return VietTienResponseDto.ok(orderService.getOrderRetailById(id), MessageUtil.GET_ID_ORDER);
//    }

    @PostMapping(value = "update", consumes = {"multipart/form-data"})
    @ApiOperation("Update order shop")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestPart(value = "json") String json) throws VietTienException, VietTienInvalidParamsException {
        OrderRequest orderRequest = (OrderRequest) StringUtil.jsonToObject(json, OrderRequest.class);
        return VietTienResponseDto.ok(orderService.createOrUpdateOrder(orderRequest), MessageUtil.SAVE_ORDER);
    }

}
