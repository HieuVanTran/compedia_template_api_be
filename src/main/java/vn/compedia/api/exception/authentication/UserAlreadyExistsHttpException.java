package vn.compedia.api.exception.authentication;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class UserAlreadyExistsHttpException extends HttpException {
    private static final long serialVersionUID = -5202433948475658078L;

    public UserAlreadyExistsHttpException() {
        super("Email is invalid or already taken", HttpStatus.CONFLICT);
    }
}
