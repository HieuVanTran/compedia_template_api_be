package vn.compedia.api.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import vn.compedia.api.util.MessageUtil;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter

public class AccountNeResponse {
    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("role_id")
    private Integer roleId;

    @JsonProperty("username")
    @Size(max = 50, message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("creat_date")
    private String createDate;

    @JsonProperty("update_date")
    private String update_date;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("code_role")
    private String codeRole;




}
