package vn.compedia.api.exception.authentication;

public class TokenValidationException extends AuthenticationException {

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException() {
    }
}
