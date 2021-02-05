package vn.compedia.api.response.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.entity.OrderDetail;
import vn.compedia.api.entity.Orders;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends Orders {

    @JsonProperty("order_detail_list")
    private List<OrderDetail> orderDetailRequestList;
}
