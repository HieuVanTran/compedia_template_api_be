package vn.compedia.api.exception;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Map;

public class VietTienInvalidParamsException extends Exception {

    private Map<String, String> errors = Maps.newHashMap();

    public VietTienInvalidParamsException(String message) {
        super(message);
        this.errors.put("ERROR", message);
    }

    public VietTienInvalidParamsException(Map<String, String> errors) {
        super(new ArrayList<>(errors.values()).get(0));
        this.errors = errors;
    }

    public VietTienInvalidParamsException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
