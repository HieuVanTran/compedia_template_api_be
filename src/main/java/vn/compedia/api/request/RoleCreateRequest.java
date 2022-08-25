package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleCreateRequest {
    @JsonProperty("role_id")
    private Long roleId;

    private String codeRole;

    private String name;
}
