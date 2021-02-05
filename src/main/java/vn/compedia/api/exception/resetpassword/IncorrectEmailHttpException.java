package vn.compedia.api.exception.resetpassword;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class IncorrectEmailHttpException extends HttpException {
    public IncorrectEmailHttpException() {
        // TODO check http status
        super("Email is invalid or doesn't registered", HttpStatus.FORBIDDEN);
    }
}
