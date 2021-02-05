package vn.compedia.api.response;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.VietTienInvalidParamsException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class ApiExceptionResponse<T> extends VietTienResponseDto<T> {

    public static ResponseEntity<VietTienResponseDto<?>> build(Exception e) {
        HttpStatus httpStatus;
        Map<String, String> errors = null;
        String customMessage = e.getMessage();

        switch (e.getClass().getSimpleName()) {
            case "MasterSalesException":
                httpStatus = HttpStatus.ACCEPTED;
                break;
            case "MasterSalesNotFoundException":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "SaveDataException":
                httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
                break;
            case "MasterSalesInvalidParamsException":
                VietTienInvalidParamsException paramsException = (VietTienInvalidParamsException) e;
                httpStatus = HttpStatus.BAD_REQUEST;
                errors = paramsException.getErrors();
                break;
            case "MaxUploadSizeExceededException":
                MaxUploadSizeExceededException exceededException = (MaxUploadSizeExceededException) e;
                Matcher p = Pattern.compile("([0-9]+)").matcher(e.getMessage());
                if (p.find()) {
                    customMessage = ", you has uploaded " + p.group(1);
                }
                if (p.find()) {
                    customMessage = "Max upload file size is " + p.group(1) + customMessage;
                }
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case "MissingServletRequestParameterException":
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                log.error(e);
                break;
        }

        return new ApiExceptionResponse<>()
                .withHttpStatus(httpStatus)
                .withErrors(errors)
                .withMessage(customMessage == null ? e.toString() : customMessage)
                .toResponseEntity();
    }

}
