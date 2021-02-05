package vn.compedia.api.response.product;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProductSearchResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("category_product_id")
    private String categoryProductId;

    @JsonProperty("description")
    private String description;

    @JsonProperty(value = "price_start")
    private Double priceStart;

    @JsonProperty(value = "price_end")
    private Double priceEnd;

}
