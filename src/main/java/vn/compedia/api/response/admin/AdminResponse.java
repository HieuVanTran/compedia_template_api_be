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

    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonProperty("code_role")
    private String codeRole;


}
