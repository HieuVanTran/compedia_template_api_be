package vn.compedia.api.response.ShopManage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopDetailResponse extends ShopListResponse {
    @JsonProperty("address")
    private String address;

    private String email;

    private String facebook;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("province_name")
    private String provinceName;

    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("district_Name")
    private String districtName;

    @JsonProperty("commune_id")
    private Long communeId;

    @JsonProperty("commune_Name")
    private String communeName;

    private String description;

    @JsonProperty("customer_role_id")
    private Long customerRoleId;

    private Double discount;
}
