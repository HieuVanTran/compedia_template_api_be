package vn.compedia.api.response.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffResponse {

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("name_staff")
    private String nameStaff;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;
}


