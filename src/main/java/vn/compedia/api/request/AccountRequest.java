package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.util.MessageUtil;

import javax.validation.constraints.Email;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {

    @JsonProperty("full_name")
    @Length(max = 50, message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String fullName;

    @JsonProperty("phone")
    @Length(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String phone;

    @JsonProperty("dob")
    private Date dob;

    @JsonProperty("gender")
    private Integer gender;

    @Email
    @JsonProperty("email")
    @Length(max = 11, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String email;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("commune_id")
    private Long communeId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("images")
    private MultipartFile images;

    @JsonProperty("password")
    private String password;

    public AccountRequest(MultipartFile images, String fullName, String phone, Date dob, Integer gender, String email, Long provinceId, Long districtId, Long communeId, String address) {
        this.images = images;
        this.fullName = fullName;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.communeId = communeId;
        this.address = address;
    }

}
