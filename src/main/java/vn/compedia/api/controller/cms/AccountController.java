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
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.response.AccountResponse;
import vn.compedia.api.response.admin.AccountNeResponse;
import vn.compedia.api.response.admin.AdminResponse;
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
        List<AccountNeResponse> list = accountService.getAll();
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
                                    @RequestParam(name = "role_id", required = false) Integer roleId,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "code_role", required = false) String codeRole,
                                    @RequestParam(name = "full_name", required = false) String fullName,
                                    @RequestParam(name ="sort_field", required = false) String sortField,
                                    @RequestParam(name ="sort_order", required = false) String sortOrder) {
        Page<AdminResponse> list = accountService.search(username, email, roleId,codeRole,fullName , sortField,sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");

    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AdminCreateRequest request) {
        try {
            accountService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody AdminCreateRequest request) {
      try {
          accountService.update(request);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
      }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        accountService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}