package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.util.MessageUtil;

@Api(tags = "Language")
@RestController
@RequestMapping("/api/v1/language")
@Validated
public class LanguageController extends GlobalExceptionHandler {

    @GetMapping("/{language}")

    @ApiOperation("Set language")
    public ResponseEntity<?> getLanguage(@PathVariable Integer id) throws VietTienException, VietTienInvalidParamsException {

        return VietTienResponseDto.ok(null, MessageUtil.SELECT_LANGUAGE);
    }

}
