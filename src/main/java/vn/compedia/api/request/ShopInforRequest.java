package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.util.MessageUtil;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopInforRequest {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("name")
    @Size(max = 50)
    private String name;

    @Size(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String phone;

    @Size(max = 50, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String email;

    @Size(max = 200, message = MessageUtil.FACEBOOK_HAS_MAX_LENGTH)
    private String facebook;

    private String description;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("commune_id")
    private Long communeId;

    @JsonProperty("address")
    private String address;

    @Size(max = 200)
    private String logo;

    @JsonProperty("customer_role_id")
    private Long customerRoleId;

    private Integer status;

    @JsonProperty("language_id")
    private Integer languageId;
}
