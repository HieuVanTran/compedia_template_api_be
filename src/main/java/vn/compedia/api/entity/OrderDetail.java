package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    @JsonProperty("order_detail_id")
    private Long orderDetailId;

    @Column(name = "order_id")
    @JsonProperty("order_id")
    private Long orderId;

    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long productId;

    @Column(name = "product_type_id")
    @JsonProperty("product_type_id")
    private Long productTypeId;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Long quantity;

    @Column(name = "price")
    @JsonProperty("price")
    private Double price;

    @Column(name = "discount_input")
    @JsonProperty("discount_input")
    private Double discountInput;

    @Column(name = "discount_by_role")
    @JsonProperty("discount_by_role")
    private Double discountByRole;

    @Column(name = "product_price")
    @JsonProperty("product_price")
    private Double productPrice;

    @Column(name = "total_money")
    @JsonProperty("total_money")
    private Double totalMoney;
}
