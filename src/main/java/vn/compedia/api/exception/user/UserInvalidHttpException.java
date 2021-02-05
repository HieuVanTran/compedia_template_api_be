package vn.compedia.api.exception.user;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class UserInvalidHttpException extends HttpException {

    private static final long serialVersionUID = 2401650728998512026L;

    public UserInvalidHttpException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
