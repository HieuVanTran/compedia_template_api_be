package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.CollectMoney;
import vn.compedia.api.entity.Role;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.CollectMoneyCreateRequest;
import vn.compedia.api.request.RoleCreateRequest;
import vn.compedia.api.service.CollectMoneyService;
import vn.compedia.api.service.RoleService;

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
        List<CollectMoney> list = collectMoneyService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long cardID) {
        CollectMoney collectMoney = collectMoneyService.getOne(cardID);
        return VietTienResponseDto.ok(collectMoney, "Get list account success");
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
    public ResponseEntity<?> delete(@RequestParam Long id){
        collectMoneyService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}



