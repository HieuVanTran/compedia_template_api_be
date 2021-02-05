package vn.compedia.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestorePasswordDTO {

    @NotNull
    @NotEmpty
    private String token;

    @NotNull
    @NotEmpty
    private String newPassword;

    @NotNull
    @NotEmpty
    private String confirmPassword;

}
