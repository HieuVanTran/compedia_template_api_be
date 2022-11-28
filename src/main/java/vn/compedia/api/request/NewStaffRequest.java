package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewStaffRequest {
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

    @JsonProperty("position")
    private String position;

    @JsonProperty("department")
    private String department;

    @JsonProperty("status")
    private int status;

    @JsonProperty("dateCreate")
    private String dateCreate;

    @JsonProperty("creator")
    private String creator;
}
