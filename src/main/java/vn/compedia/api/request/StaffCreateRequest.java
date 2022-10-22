package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import vn.compedia.api.util.MessageUtil;

@Getter
@Setter
public class StaffCreateRequest {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name_staff")
    private String nameStaff;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

}
