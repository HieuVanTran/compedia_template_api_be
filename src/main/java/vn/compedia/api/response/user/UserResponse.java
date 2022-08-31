package vn.compedia.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("expiration_date")
    private String expirationDate;

    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("card_number")
    private String cardNumber;

//    @JsonProperty("updated_date")
//    private String updatedDate;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("email")
    private String email;


}
