package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.ApiResponseDto;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.authentication.PasswordsDontMatchException;
import vn.compedia.api.exception.changepassword.*;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.request.ChangePassRequest;
import vn.compedia.api.response.admin.AccountNeResponse;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.service.AccountService;

import javax.validation.Valid;
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
    public ResponseEntity<?> getOne() {
        try {
            AccountNeResponse admin = accountService.getOne();
            return VietTienResponseDto.ok(admin, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "roleId", required = false) Integer roleId,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "codeRole", required = false) String codeRole,
                                    @RequestParam(name = "fullName", required = false) String fullName,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder) {
        Page<AdminResponse> list = accountService.search(username, email, roleId, codeRole, fullName, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");

    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AdminCreateRequest request) {
        try {
            accountService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody AdminCreateRequest request) {
        try {
            accountService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        accountService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePassRequest request) {
        try {
            accountService.changePassword(request);
            return ApiResponseDto.createdWithMessage("Đổi mật khẩu thành công!", HttpStatus.OK);
        } catch (PasswordOldNotValid e) {
            return ApiResponseDto.createdWithMessage("Mật khẩu cũ không đúng", HttpStatus.BAD_REQUEST);
        } catch (PasswordsDontMatchException e) {
            return ApiResponseDto.createdWithMessage("Mật khẩu không khớp", HttpStatus.BAD_REQUEST);
        } catch (PasswordNewNotMathRegex passwordNewNotMathRegex) {
            return ApiResponseDto.createdWithMessage("Mật khẩu mới phải có ít nhất 6 ký tự, bao gồm ký tự số, chữ in hoa, chữ in thường", HttpStatus.BAD_REQUEST);
        } catch (PasswordOldNotFoundException e) {
            return ApiResponseDto.createdWithMessage("Mật khẩu cũ không được để trống", HttpStatus.BAD_REQUEST);
        } catch (PasswordNewNotFoundException e) {
            return ApiResponseDto.createdWithMessage("Mật khẩu mới không được để trống", HttpStatus.BAD_REQUEST);
        } catch (NewPasswordMatchOldPassword e) {
            return ApiResponseDto.createdWithMessage("Mật khẩu mới không được trùng với mật khẩu cũ", HttpStatus.BAD_REQUEST);
        }
    }
}