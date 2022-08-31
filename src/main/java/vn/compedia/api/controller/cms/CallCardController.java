package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.response.book.CallCardResponse;
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
        try {
            callCardService.createCallCard(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CallCardCreateRequest request) {
        try {
            callCardService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<CallCardResponse> list = callCardService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long collectMoneyId) {
        try {
            CallCardResponse loan = callCardService.getOne(collectMoneyId);
            return VietTienResponseDto.ok(loan, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sort_field", required = false) String sortField,
                                    @RequestParam(name = "sort_order", required = false) String sortOrder,
                                    @RequestParam(name = "nameStaff", required = false) String nameStaff,
                                    @RequestParam(name = "status", required = false) Integer status) {
        Page<CallCardResponse> list = callCardService.search(username, status, nameStaff, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        callCardService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}