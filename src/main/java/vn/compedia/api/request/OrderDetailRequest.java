package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequest {
    @JsonProperty("order_detail_id")
    private Long orderDetailId;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("product_type_id")
    private Long productTypeId;

    @JsonProperty("quantity")
    private Long quantity;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("discount")
    private Double discount;

    @JsonProperty("product_price")
    private Double productPrice;

    @JsonProperty("total_money")
    private Double totalMoney;

}
