package vn.compedia.api.exception.resetpassword;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class TokenNotFoundOrExpiredHttpException extends HttpException {
    public TokenNotFoundOrExpiredHttpException() {
        super("Reset password request wasn't performed or already expired", HttpStatus.FORBIDDEN);
    }
}
