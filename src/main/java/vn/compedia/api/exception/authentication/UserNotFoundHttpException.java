package vn.compedia.api.exception.authentication;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class UserNotFoundHttpException extends HttpException {
    private static final long serialVersionUID = 4770986620665158856L;

    public UserNotFoundHttpException(String message, HttpStatus status) {
        super(message, status);
    }
}
