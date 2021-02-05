package vn.compedia.api.exception;

public class VietTienNotFoundException extends VietTienException {

    private static final long serialVersionUID = 1L;

    public VietTienNotFoundException(Class entity) {
        super(entity.getSimpleName() + " not found!");
    }

    public VietTienNotFoundException(String message) {
        super(message);
    }
}
