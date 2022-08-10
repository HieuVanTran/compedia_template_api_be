package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import vn.compedia.api.util.MessageUtil;

@Getter
@Setter
public class StaffCreateRequest {
    Long Id;

    @JsonProperty("name_staff")
    private String nameStaff;

    @JsonProperty("phone_number")
    @Length(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String phoneNumber;

    private String address;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;


}
