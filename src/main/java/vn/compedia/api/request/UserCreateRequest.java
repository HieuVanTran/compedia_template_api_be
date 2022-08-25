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

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("create_date")
    private String createDate;


}
