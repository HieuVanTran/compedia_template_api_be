package vn.compedia.api.exception;

public class SaveDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SaveDataException(String message) {
        super(message);
    }
}
