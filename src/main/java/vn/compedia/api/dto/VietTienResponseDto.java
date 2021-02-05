package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VietTienResponseDto<T> {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonIgnore
    private HttpHeaders httpHeaders;

    private int code;

    private T data;

    private Map<?, ?> errors;

    private String message = "Success";

    public static <T> VietTienResponseDto<T> build() {
        return new VietTienResponseDto<>();
    }

    public static @ResponseBody
    ResponseEntity<?> ok(Object data, String message) {
        return build().withHttpStatus(HttpStatus.OK)
                .withData(data)
                .withMessage(message)
                .toResponseEntity();
    }

    @PostConstruct
    private void init() {
        httpStatus = HttpStatus.OK;
        code = httpStatus.value();
    }

    public VietTienResponseDto<T> withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        return this;
    }

    public VietTienResponseDto<T> withData(T data) {
        this.data = data;
        return this;
    }

    public VietTienResponseDto<T> withHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
        return this;
    }

    public VietTienResponseDto<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    public VietTienResponseDto<T> withErrors(Map<?, ?> errors) {
        this.errors = errors;
        return this;
    }

    public ResponseEntity<VietTienResponseDto<?>> toResponseEntity() {
        return new ResponseEntity<>(this, httpHeaders, httpStatus);
    }

    public ResponseEntity<Object> toObject() {
        return new ResponseEntity<>(this, this.httpHeaders, this.httpStatus);
    }
}
