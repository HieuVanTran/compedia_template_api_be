package vn.compedia.api.exception;

public class VietTienException extends Exception {

    private String message;

    public VietTienException(String message) {
        super(message);
        this.message = message;
    }

    public VietTienException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public VietTienException(Throwable cause) {
        super(cause);
    }

    public VietTienException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message;
    }
}
