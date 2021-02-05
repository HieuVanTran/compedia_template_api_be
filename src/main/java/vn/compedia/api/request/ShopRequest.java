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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopRequest {

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("name")
    @Length(max = 50, message = MessageUtil.NAME_HAS_MAX_LENGTH)
    private String name;

    @JsonProperty("phone")
    @Length(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String phone;

    @Email
    @JsonProperty("email")
    @Length(max = 50, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String email;

    @JsonProperty("facebook")
    @Length(max = 200, message = MessageUtil.FACEBOOK_HAS_MAX_LENGTH)
    private String facebook;

    @JsonProperty("logo")
    private MultipartFile logo;

    @JsonProperty("description")
    private String description;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("commune_id")
    private Long communeId;

    @JsonProperty("address")
    @Length(max = 100, message = MessageUtil.ADDRESS_HAS_MAX_LENGTH)
    private String address;

}
