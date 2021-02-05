package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long productId;

    @Column(name = "shop_id")
    @JsonProperty("shop_id")
    private Long shopId;

    @Column(name = "category_product_id")
    @JsonProperty("category_product_id")
    private Long categoryProductId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "object")
    private String object;

    @Column(name = "retail_price")
    @JsonProperty("retail_price")
    private Double retailPrice;

    @Column(name = "wholesale_prices")
    @JsonProperty("wholesale_prices")
    private Double wholesalePrices;

    @Column(name = "images")
    private String images;

    @Column(name = "type")
    private Integer type;

    @Column(name = "quantity_in_stock")
    @JsonProperty("quantity_in_stock")
    private Long quantity_in_stock;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "email")
    private String email;

    @Column(name = "province_id")
    @JsonProperty("province_id")
    private Long provinceId;

    @Column(name = "district_id")
    @JsonProperty("district_id")
    private Long districtId;

    @Column(name = "commune_id")
    @JsonProperty("commune_id")
    private Long communeId;

    @Column(name = "is_first_login")
    @JsonProperty("is_first_login")
    private boolean firstLogin;

    @Column(name = "description")
    private String description;

}
