package vn.compedia.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.repository.ProductCategoryRepository;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.MessageUtil;

@Api(tags = "Category")
@RestController
@RequestMapping("/api/v1/product_category")
@Validated
public class ProductCategoryController extends GlobalExceptionHandler {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @GetMapping("get_all")
    @ApiOperation(value = "Get all category")
    public ResponseEntity<?> getAll() {
        return VietTienResponseDto.ok(productCategoryRepository.getAllByStatus(DbConstant.ACTIVE_STATUS), MessageUtil.GET_ALL_SHOP);
    }

}
