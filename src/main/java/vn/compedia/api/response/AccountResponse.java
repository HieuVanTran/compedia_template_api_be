package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    @JsonProperty("date_of_birth")
    @ApiModelProperty(example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date dateOfBirth;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("age")
    private Integer age;
    @JsonProperty("sex")
    private Integer sex;

    @JsonProperty("avatar_path")
    private String avatarPath;

    @JsonProperty("audio_path")
    private String audioPath;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("more_info")
    private String moreInfo;

    @JsonProperty("married_status")
    private Integer marriedStatus;

    @JsonProperty("job")
    private String job;

}
