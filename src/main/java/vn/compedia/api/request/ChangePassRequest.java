package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter

public class ChangePassRequest {

    @NotEmpty
    @JsonProperty("old_password")
    private String oldPassword;
    @NotEmpty
    @JsonProperty("new_password")
    private String newPassword;
    @NotEmpty
    @JsonProperty("re_password")
    private String rePassword;

}
