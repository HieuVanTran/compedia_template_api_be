package vn.compedia.api;

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
public class ApiResponseDto<T> {

    @JsonIgnore
    private HttpStatus httpStatus;

    @JsonIgnore
    private HttpHeaders httpHeaders;

    private int code;

    private String resCode;

    private T data;

    private Map<?, ?> errors;

    private String message = "Thành công";

    public static <T> ApiResponseDto<T> build() {
        return new ApiResponseDto<>();
    }

    public static @ResponseBody
    ResponseEntity<?> ok(Object data, String message) {
        return build().withHttpStatus(HttpStatus.OK)
                .withData(data)
                .withMessage(message)
                .toResponseEntity();
    }

    public static @ResponseBody
    ResponseEntity<?> data(Object data) {
        return build().withHttpStatus(HttpStatus.OK)
                .withData(data)
                .toResponseEntity();
    }

    public static @ResponseBody
    ResponseEntity<?> createdWithMessage(String message, HttpStatus status) {
        return build().withHttpStatus(status)
                .withMessage(message)
                .toResponseEntity();
    }

    @PostConstruct
    private void init() {
        httpStatus = HttpStatus.OK;
        code = httpStatus.value();
    }

    public ApiResponseDto<T> withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        return this;
    }

    public ApiResponseDto<T> withData(T data) {
        this.data = data;
        return this;
    }

    public ApiResponseDto<T> withCode(String resCode) {
        this.resCode = resCode;
        return this;
    }


    public ApiResponseDto<T> withMessage(String message) {
        this.message = message;
        return this;
    }


    public ResponseEntity<ApiResponseDto<?>> toResponseEntity() {
        return new ResponseEntity<>(this, httpHeaders, httpStatus);
    }
}



