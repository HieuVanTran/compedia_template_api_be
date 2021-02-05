package vn.compedia.api.controller;

import com.google.maps.errors.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.service.StoreService;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.utility.PropertiesUtil;

import java.io.IOException;
import java.util.Map;

@Api(tags = "Warehouse")
@RestController
@RequestMapping("/api/v1/warehouse")
@Validated
public class StoreController extends GlobalExceptionHandler {

    @Autowired
    private StoreService storeService;

    @PostMapping("warehouse")
    @ApiOperation(value = "warehouse")
    public ResponseEntity<?> warehouse(@RequestBody Map<String, Object> filters,
                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "pageSize", defaultValue = "25") int pageSize,
                                       @RequestParam(value = "language_id") Integer languageId) throws VietTienInvalidParamsException, InterruptedException, IOException, ApiException {
        return VietTienResponseDto.ok(storeService.warehouse(filters, page, pageSize, languageId), PropertiesUtil.getMessage(MessageUtil.WAREHOUSE_GET_ALL, languageId));
    }

}
