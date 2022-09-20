package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FunctionRoleResponse {

    @JsonProperty ("function_role_id")
    private Long functionRoleId;

    @JsonProperty ("role_id")
    private Integer roleId;

    @JsonProperty ("name_role")
    private String nameRole;

    @JsonProperty ("code_role")
    private String codeRole;

    @JsonProperty ("function")
    private String function;

    @JsonProperty ("status")
    private Integer status;

}
