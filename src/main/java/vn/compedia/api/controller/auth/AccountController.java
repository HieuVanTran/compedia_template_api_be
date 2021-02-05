package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.request.AccountRequest;
import vn.compedia.api.service.AccountService;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;

import javax.validation.constraints.Email;
import java.util.Date;

@Api(tags = "Account")
@RestController
@RequestMapping("/api/v1/account")
@Validated
public class AccountController extends GlobalExceptionHandler {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get info account current")
    public ResponseEntity<?> current() throws VietTienNotFoundException {
        return VietTienResponseDto.ok(accountService.detail(UserContextHolder.getUser().getAccountId()), "Lấy ra chi tiết tài khoản thành công");
    }

    @GetMapping("check")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get status update information account")
    public ResponseEntity<?> getStatusUpdate() throws VietTienNotFoundException {
        return VietTienResponseDto.ok(accountService.getStatusUpdate(UserContextHolder.getUser().getAccountId()), "Kiểm tra thông tin cập nhật thành công");
    }

    @PostMapping("update")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Update info account current")
    public ResponseEntity<?> update(
            @RequestParam(name = "images", required = false) MultipartFile images,
            @RequestParam(name = "full_name", required = false) @Length(max = 50) String fullName,
            @RequestParam(name = "phone", required = false) @Length(max = 11) String phone,
            @RequestParam(name = "dob", required = false) Date dob,
            @RequestParam(name = "gender", required = false) Integer gender,
            @RequestParam(name = "email", required = false) @Email @Length(max = 50) String email,
            @RequestParam(name = "province_id", required = false) Long provinceId,
            @RequestParam(name = "district_id", required = false) Long districtId,
            @RequestParam(name = "commune_id", required = false) Long communeId,
            @RequestParam(name = "address", required = false) String address
    ) throws VietTienInvalidParamsException, VietTienException {
        AccountRequest accountRequest = new AccountRequest(images, fullName, phone, dob, gender, email, provinceId, districtId, communeId, address);
        return VietTienResponseDto.ok(accountService.update(accountRequest), "Cập nhật tài khoản thành công");
    }

    @PostMapping(value = "forget_password")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Update info account current")
    public ResponseEntity<?> sendEmail(
            @RequestParam Integer language,
            @RequestParam String email) throws VietTienException, VietTienInvalidParamsException {

        // Validate

        accountService.onCheckEmail(language, email);
        return VietTienResponseDto.ok(null, MessageUtil.SUBMITTED_SUCCESSFULLY);
    }

    @PostMapping(value = "confirmation_otp")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Confirmation OTP")
    public ResponseEntity<?> confirmationOtpAndForgetPassWord(
            @RequestParam String language,
            @RequestParam String email,
            @RequestParam String passWord) throws VietTienException, VietTienInvalidParamsException {
        // Validate
        if (!StringUtil.validateLanguge(language)) {
            throw new VietTienException("Language is invalid");
        }
        accountService.confirmationOtpAndForgetPassWord(language, email, passWord);
        return VietTienResponseDto.ok(null, MessageUtil.PASSWORD_CHANGED_SUCCESSFULLY);
    }

    @PostMapping(value = "check_otp")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Check OTP")
    public ResponseEntity<?> checkOTP(
            @RequestParam(name = "language_id") Integer languageId,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "otp", required = false) String otp
    ) throws VietTienException, VietTienInvalidParamsException {
        // Validate
        accountService.checkOTP(languageId, email, otp);
        return VietTienResponseDto.ok(null, MessageUtil.ACCEPT_OTP);
    }

    @PostMapping("change_password")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Change Password")
    public ResponseEntity<?> changePassWord(
            @RequestParam(name = "language") int language,
            @RequestParam(name = "password") String password,
            @RequestParam String newPassWord) throws VietTienException, VietTienInvalidParamsException {
        // Validate
        accountService.changePassWord(language, password, newPassWord);
        return VietTienResponseDto.ok(null, MessageUtil.PASSWORD_CHANGED_SUCCESSFULLY);
    }
}
