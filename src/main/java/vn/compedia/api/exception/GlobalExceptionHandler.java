package vn.compedia.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import vn.compedia.api.response.ApiExceptionResponse;

import java.lang.reflect.Field;
import java.util.Map;

@Log4j2
@AllArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<?> handleException(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            return handleValidationException((MethodArgumentNotValidException) e);
        }

        return ApiExceptionResponse.build(e);
    }

    private ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        try {
            Map<String, String> errors = Maps.newHashMap();
            Class<?> aClass = e.getParameter().getParameterType();

            for (final FieldError error : e.getBindingResult().getFieldErrors()) {
                Field field = aClass.getDeclaredField(error.getField());
                String fieldName = field.isAnnotationPresent(JsonProperty.class) ? field.getAnnotation(JsonProperty.class).value() : field.getName();
                errors.put(fieldName, fieldName + " " + error.getDefaultMessage());
            }
            for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
                Field field = aClass.getDeclaredField(error.getObjectName());
                String fieldName = field.isAnnotationPresent(JsonProperty.class) ? field.getAnnotation(JsonProperty.class).value() : field.getName();
                errors.put(fieldName, fieldName + " " + error.getDefaultMessage());
            }

            return ApiExceptionResponse.build(new VietTienInvalidParamsException(errors));
        } catch (Exception ex) {
            return ApiExceptionResponse.build(ex);
        }
    }

}
