package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.service.CallCardService;

import java.util.List;

@Api(tags = "CallCardController")
@RestController
@RequestMapping("/api/v1/call-card")
@Validated

public class CallCardController extends GlobalExceptionHandler {

    @Autowired
    private CallCardService callCardService;

    @PostMapping()
    public ResponseEntity<?> createCallCard(@RequestBody CallCardCreateRequest request) {
        callCardService.createCallCard(request);
        return VietTienResponseDto.ok("", "Get list account success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CallCardCreateRequest request){
        callCardService.update(request);
        return VietTienResponseDto.ok("","Get list account success");
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<CallCard> list = callCardService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long roleId) {
        CallCard loan = callCardService.getOne(roleId);
        return VietTienResponseDto.ok(loan, "Get list account success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        callCardService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}