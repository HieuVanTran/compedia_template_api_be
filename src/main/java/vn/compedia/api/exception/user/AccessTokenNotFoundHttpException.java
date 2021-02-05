package vn.compedia.api.exception.user;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class AccessTokenNotFoundHttpException extends HttpException {

    public AccessTokenNotFoundHttpException(String message, HttpStatus status) {
        super(message, status);
    }
}
