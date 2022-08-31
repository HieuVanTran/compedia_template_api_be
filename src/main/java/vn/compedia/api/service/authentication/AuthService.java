package vn.compedia.api.service.authentication;

import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.auth.LoginDTO;
import vn.compedia.api.dto.auth.RefreshTokenDTO;
import vn.compedia.api.dto.auth.api.ApiSignUpDTO;
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.authentication.InvalidTokenHttpException;
import vn.compedia.api.exception.authentication.UserAlreadyExistsHttpException;
import vn.compedia.api.exception.authentication.UserNotFoundHttpException;
import vn.compedia.api.exception.user.UserAlreadyExistsException;
import vn.compedia.api.exception.user.UserNotFoundException;
import vn.compedia.api.repository.auth.ApiUserRepository;
import vn.compedia.api.service.ApiUserService;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.authentication.Tokens;

import java.util.Optional;

@Log4j2
@Service
public class AuthService {

    private ApiUserService apiUserService;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    public AuthService(ApiUserService apiUserService,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.apiUserService = apiUserService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public Tokens register(ApiSignUpDTO apiSignUpDTO) throws UserAlreadyExistsHttpException {
        try {
            Account account = apiUserService.register(apiSignUpDTO);
            return createToken(account);
        } catch (UserAlreadyExistsException exception) {
            throw new UserAlreadyExistsHttpException();
        }
    }

    public Tokens login(LoginDTO loginDTO) throws UserNotFoundHttpException {
        try {
            Authentication authentication = createAuthentication(loginDTO);
            BundleUserDetailsService.BundleUserDetails userDetails =
                    (BundleUserDetailsService.BundleUserDetails) authenticationManager
                            .authenticate(authentication).getPrincipal();

            Account account = userDetails.getAccount();
            return createToken(account);
        } catch (AuthenticationException exception) {
            log.error(exception);
            throw new UserNotFoundHttpException(MessageUtil.INCORRECT_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    public Tokens refreshToken(RefreshTokenDTO refreshTokenDTO) throws InvalidTokenHttpException {
        try {
            String email = tokenService.getEmailFromRefreshToken(refreshTokenDTO.getTokens().getRefreshToken());
            Account account = apiUserService.findByEmail(email);
            return createToken(account);
        } catch (JwtException | UserNotFoundException e) {
            throw new InvalidTokenHttpException();
        }
    }

    private Authentication createAuthentication(LoginDTO loginDTO) {
        Optional<Account> account = apiUserRepository.findByEmailAndRoleId(loginDTO.getEmail(), DbConstant.ROLE_ID_ADMIN);
        return new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword() + account.get().getSalt());
    }

    private Tokens createToken(Account account) {
        return tokenService.createToken(account);
    }

}
