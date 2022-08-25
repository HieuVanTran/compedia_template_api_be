package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.response.CallCardDetailsResponse;
import vn.compedia.api.service.CallCardDetailsService;

import java.util.List;

@Api(tags = "CallCardControllerDetails")
@RestController
@RequestMapping("/api/v1/call-card-details")
@Validated

public class CallCardDetailsController extends GlobalExceptionHandler {

    @Autowired
    private CallCardDetailsService callCardDetailsService;


    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<CallCardDetailsResponse> list = callCardDetailsService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long callCardDetailsId) {
        try {
            CallCardDetailsResponse loan = callCardDetailsService.getOne(callCardDetailsId);
            return VietTienResponseDto.ok(loan, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}