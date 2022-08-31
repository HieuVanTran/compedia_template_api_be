package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateRequest {

    private Long Id;
    @JsonProperty("full_name")
    private String fullName;


    private String DOB;

    private String email;


    private String phone;

    private String username;

    private String password;

    @JsonProperty("role_id")
    private Integer roleId;


}
