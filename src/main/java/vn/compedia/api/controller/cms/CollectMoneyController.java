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
import vn.compedia.api.request.CollectMoneyCreateRequest;
import vn.compedia.api.response.book.CollectMoneyResponse;
import vn.compedia.api.service.CollectMoneyService;

import java.util.List;

@Api(tags = "CollectMoney")
@RestController
@RequestMapping("/api/v1/collect-money")
@Validated


public class CollectMoneyController extends GlobalExceptionHandler {

    @Autowired
    private CollectMoneyService collectMoneyService;

    @GetMapping(value = "collect-money")
    public ResponseEntity<?> getAll() {
        List<CollectMoneyResponse> list = collectMoneyService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long callCardId) {
        try {
            CollectMoneyResponse loan = collectMoneyService.getOne(callCardId);
            return VietTienResponseDto.ok(loan, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "fullName", required = false) String fullName,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder,
                                    @RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "staffId", required = false) Long staffId) {
        Page<CollectMoneyResponse> list = collectMoneyService.search(fullName, staffId, username, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CollectMoneyCreateRequest request) {
        collectMoneyService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CollectMoneyCreateRequest request) {
        collectMoneyService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        collectMoneyService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}



