package vn.compedia.api.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AdminResponse {

    @JsonProperty("account_id")
    private Long accountId;

    private String username;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("full_name")
    private String fullName;

    private String email;



}
