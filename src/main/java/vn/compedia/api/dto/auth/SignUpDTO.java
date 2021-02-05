package vn.compedia.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 11)
    private String phone;

    @NotNull
    @NotEmpty
    private String otp;

}
