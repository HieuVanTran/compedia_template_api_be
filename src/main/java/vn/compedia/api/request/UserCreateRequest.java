package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Getter
@Setter
public class UserCreateRequest {
    Long Id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("expiration_date")
    private String expirationDate;

    private String address;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("account_id")
    private Long accountId;


}
