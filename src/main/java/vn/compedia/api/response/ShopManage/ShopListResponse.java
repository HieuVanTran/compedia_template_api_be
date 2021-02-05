package vn.compedia.api.response.ShopManage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopListResponse {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("name")
    private String name;

    private String phone;

    private String logo;

    private Integer status;
}
