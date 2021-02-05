package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.config.Constant;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @JsonProperty("order_id")
    private Long orderId;

    @Column(name = "shop_id")
    @JsonProperty("shop_id")
    private Long shopId;

    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "total_money")
    @JsonProperty("total_money")
    private Double totalMoney;

    @Column(name = "note")
    @JsonProperty("note")
    private String note;

    @Column(name = "status")
    @JsonProperty("status")
    private Integer status;

    @Column(name = "discount")
    @JsonProperty("discount")
    private Double discount;

    @Column(name = "transport_fee")
    @JsonProperty("transport_fee")
    private Double transportFee;

    @Column(name = "day_shipping")
    @JsonProperty("day_shipping")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constant.TIME_ZONE_DEFAULT)
    private Date dayShipping;

    @Column(name = "type")
    @JsonProperty("type")
    private Integer type;

}
