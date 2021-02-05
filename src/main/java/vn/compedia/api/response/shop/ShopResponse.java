package vn.compedia.api.response.shop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import vn.compedia.api.util.MessageUtil;

import javax.validation.constraints.Email;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ShopResponse {

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("shop_name")
    @Length(max = 50, message = MessageUtil.NAME_HAS_MAX_LENGTH)
    private String shopName;

    @JsonProperty("shop_phone")
    @Length(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String shopPhone;

    @Email
    @JsonProperty("shop_email")
    @Length(max = 50, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String shopEmail;

    @JsonProperty("shop_facebook")
    @Length(max = 200, message = MessageUtil.FACEBOOK_HAS_MAX_LENGTH)
    private String shopFacebook;

    @JsonProperty("shop_logo")
    private String shopLogo;

    @JsonProperty("shop_description")
    private String shopDescription;

    @JsonProperty("shop_province_id")
    private Long shopProvinceId;

    @JsonProperty("shop_province_name")
    private String shopProvinceName;

    @JsonProperty("shop_district_id")
    private Long shopDistrictId;

    @JsonProperty("shop_district_name")
    private String shopDistrictName;

    @JsonProperty("shop_commune_id")
    private Long shopCommuneId;

    @JsonProperty("shop_commune_name")
    private String shopCommuneName;

    @JsonProperty("shop_address")
    @Length(max = 100, message = MessageUtil.ADDRESS_HAS_MAX_LENGTH)
    private String shopAddress;

    @JsonProperty("account_full_name")
    @Length(max = 50, message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String accountFullName;

    @JsonProperty("account_phone")
    @Length(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String accountPhone;

    @JsonProperty("account_dob")
    private Date accountDob;

    @JsonProperty("account_gender")
    private Integer accountGender;

    @Email
    @JsonProperty("account_email")
    @Length(max = 11, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String accountEmail;

    @JsonProperty("account_province_id")
    private Long accountProvinceId;

    @JsonProperty("account_province_name")
    private String accountProvinceName;

    @JsonProperty("account_district_id")
    private Long accountDistrictId;

    @JsonProperty("account_district_name")
    private String accountDistrictName;

    @JsonProperty("account_commune_id")
    private Long accountCommuneId;

    @JsonProperty("account_commune_name")
    private String accountCommuneName;

    @JsonProperty("account_images")
    private String accountImages;

    @JsonProperty("account_password")
    private String accountPassword;

}
