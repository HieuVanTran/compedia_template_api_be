package vn.compedia.api.exception.resetpassword;

import org.springframework.http.HttpStatus;
import vn.compedia.api.exception.HttpException;

public class CantSendEmailHttpException extends HttpException {
    public CantSendEmailHttpException() {
        super("Can't reset password, please, try again later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
