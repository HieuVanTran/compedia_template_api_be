package vn.compedia.api.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.entity.ProductImages;
import vn.compedia.api.entity.ProductLang;
import vn.compedia.api.entity.ProductSize;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("shop_id")
    private Long shopId;

    @JsonProperty("category_product_id")
    private Long categoryProductId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("object")
    private Integer object;

    @JsonProperty("retail_price")
    private Double retailPrice;

    @JsonProperty("wholesale_prices")
    private Double wholesalePrices;

    @JsonProperty("images")
    private String images;

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("quantity_in_stock")
    private Long quantity_in_stock;

    @JsonProperty("description")
    private String description;

    @JsonProperty("product_category_lang_name")
    private String productCategoryLangName;

    @JsonProperty("object_group_lang_name")
    private String objectGroupLangName;

    @JsonProperty("list_product_size")
    private List<ProductSize> listProductSize;

    @JsonProperty("product_lang")
    private ProductLang productLang;

    @JsonProperty("list_product_images")
    private List<ProductImages> listProductImages;

    public ProductResponse(Long productId, Long shopId, Long categoryProductId, String code, String name, Integer object, String images, Integer type, Long quantity_in_stock, String productCategoryLangName, String objectGroupLangName) {
        this.productId = productId;
        this.shopId = shopId;
        this.categoryProductId = categoryProductId;
        this.code = code;
        this.name = name;
        this.object = object;
        this.images = images;
        this.type = type;
        this.quantity_in_stock = quantity_in_stock;
        this.productCategoryLangName = productCategoryLangName;
        this.objectGroupLangName = objectGroupLangName;
    }
}
