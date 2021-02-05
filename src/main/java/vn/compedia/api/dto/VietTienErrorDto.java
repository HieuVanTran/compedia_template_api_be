package vn.compedia.api.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class VietTienErrorDto {

    private int code;

    private String message;

    private Map<String, Object> errors;
}
