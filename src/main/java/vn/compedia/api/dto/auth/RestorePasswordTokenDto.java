package vn.compedia.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RestorePasswordTokenDto {
    private String token;
    private LocalDateTime expiryDate;
}
