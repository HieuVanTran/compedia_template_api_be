package vn.compedia.api.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseResponse {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_vi")
    private String nameVi;


    @JsonProperty("images")
    private String images;

    @JsonProperty("price")
    private Double price;


    @JsonProperty("type_name")
    private String typeName;

    @JsonProperty("quantity")
    private Integer quantity;

}
