package vn.compedia.api.exception.changepassword;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordNewNotMathRegex extends Exception {
    public PasswordNewNotMathRegex() {
    }
}
