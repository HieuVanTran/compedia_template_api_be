package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProductLangChangeDto {

    @JsonProperty("product_lang_id")
    private Long productLangId;

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;


    @JsonProperty("wholesale_prices")
    private Double wholesalePrices;


    @JsonProperty("retail_price")
    private Double retailPrice;

    @JsonProperty("language_id")
    private Integer languageId;

}
