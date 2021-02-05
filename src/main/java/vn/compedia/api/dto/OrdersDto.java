package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.entity.Orders;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto extends Orders {

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("shop_name")
    private String shopName;
}
