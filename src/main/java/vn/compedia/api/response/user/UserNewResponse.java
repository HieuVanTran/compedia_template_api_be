package vn.compedia.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNewResponse {
    @JsonProperty("card_id")
    private Long cardId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("expiration_date")
    private String expirationDate;

    @JsonProperty("address")
    private String address;


}
