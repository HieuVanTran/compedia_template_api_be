package vn.compedia.api.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.util.authentication.Tokens;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenDTO {

    @NotNull
    @JsonProperty("token")
    private Tokens tokens;

    public RefreshTokenDTO(@NotEmpty @NotNull Tokens tokens) {
        this.tokens = tokens;
    }

}
