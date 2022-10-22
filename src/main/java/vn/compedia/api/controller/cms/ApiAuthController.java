package vn.compedia.api.controller.cms;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.dto.auth.LoginDTO;
import vn.compedia.api.dto.auth.RefreshTokenDTO;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.user.UserCollectMoneyValid;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.service.authentication.AuthService;
import vn.compedia.api.util.authentication.Tokens;

import javax.validation.Valid;
import java.util.Map;

@Api(tags = "API auth")
@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class ApiAuthController extends GlobalExceptionHandler {

    public static final String YYYYMMDD_FOLDER = "yyyyMMdd";
    public static final String DATE_FORMAT_UPLOAD = "ddMMyyyyHHmmssSSS";
    public static final String FILE_SEPARATOR = "/";
    private final AuthService authService;
    @Autowired
    private AccountRepository accountRepository;
    @Value("${vn.compedia.static.context}")
    private String staticContext;
    private Integer language;

    @Autowired
    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            Map<String, String> errors = Maps.newHashMap();
            if (StringUtils.isBlank(loginDTO.getEmail())) {
                errors.put("email", "Bạn vui lòng nhập địa chỉ email");
            } else if (!EmailValidator.getInstance().isValid(loginDTO.getEmail())) {
                errors.put("email", "Địa chỉ Email không đúng định dạng");
            }
            if (!errors.isEmpty()) {
                throw new VietTienInvalidParamsException(errors);
            }
            if (StringUtils.isBlank(loginDTO.getPassword())) {
                errors.put("password", "Bạn vui lòng nhập mật khẩu");
            }
            if (!errors.isEmpty()) {
                throw new VietTienInvalidParamsException(errors);
            }
            Tokens tokens = authService.login(loginDTO);
            return toResponse(tokens, "Authentication has been passed");
        } catch (UserCollectMoneyValid ex) {
            return VietTienResponseDto.withMessageAndStatus("Bạn đã vi phạm chính sách mượn sách của nhà trường khi không trả sách đúng hạn. Vui lòng liên hệ quản lý thư viện để xử lý.", HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return VietTienResponseDto.withMessageAndStatus("Có lỗi xảy ra. Bạn vui lòng thử lại sau", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("sign-out")
    public ResponseEntity<?> logout() {
        return VietTienResponseDto.ok("Ok", "Logout success");
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDTO) {
        Tokens tokens = authService.refreshToken(refreshTokenDTO);
        return toResponse(tokens, "Refresh token successfully");
    }

    private ResponseEntity<?> toResponse(Tokens tokens, String message) {
        return VietTienResponseDto.ok(new RefreshTokenDTO(tokens), message);
    }

}
