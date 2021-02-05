package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.config.Constant;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("total_money")
    private Double totalMoney;

    @JsonProperty("note")
    private String note;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("discount")
    private Double discount;

    @JsonProperty("transport_fee")
    private Double transportFee;

    @JsonProperty("day_shipping")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constant.TIME_ZONE_DEFAULT)
    private Date dayShipping;

    @JsonProperty("order_detail_list")
    private List<OrderDetailRequest> orderDetailRequestList;

}
