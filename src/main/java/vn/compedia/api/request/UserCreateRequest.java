package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    Long Id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("expiration_date")
    private String expirationDate;

    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("create_date")
    private String createDate;


}
