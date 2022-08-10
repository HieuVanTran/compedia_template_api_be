package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.response.AccountResponse;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.service.AccountService;

import java.util.List;

@Api(tags = "Account")
@RestController
@RequestMapping("/api/v1/account")
@Validated
public class AccountController extends GlobalExceptionHandler {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "account")
    public ResponseEntity<?> getAll() {
        List<Account> list = accountService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long acountId) {
       Account account = accountService.getOne(acountId);
        return VietTienResponseDto.ok(account, "Get list account success");
    }
    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "role_id", required = false) String roleId,
                                    @RequestParam(name = "full_name", required = false) String fullName) {
        List<AdminResponse> list = accountService.search(username, email,roleId,fullName);
        return VietTienResponseDto.ok(list, "Search list book success");
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AdminCreateRequest request) {
        accountService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody AdminCreateRequest request) {
        accountService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        accountService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}
