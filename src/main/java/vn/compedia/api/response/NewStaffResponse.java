package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NewStaffResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("codeStaff")
    private String codeStaff;

    @JsonProperty("nameStaff")
    private String nameStaff;

    @JsonProperty("dateStaff")
    private String dateStaff;

    @JsonProperty("sexStaff")
    private String sexStaff;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("position_id")
    private String position_id;

    @JsonProperty("department_id")
    private String department_id;

    @JsonProperty("status")
    private int status;

    @JsonProperty("dateCreate")
    private String dateCreate;

    @JsonProperty("creator")
    private String creator;

}
