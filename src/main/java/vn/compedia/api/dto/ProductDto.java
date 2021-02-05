package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.entity.Product;
import vn.compedia.api.entity.ProductLang;
import vn.compedia.api.entity.ProductSize;

import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProductDto extends Product {

    @JsonProperty("list_product_lang")
    private List<ProductLang> productLangList;


    @JsonProperty("list_product_size")
    private List<ProductSize> listProductSize;

    @Transient
    @JsonIgnore
    private MultipartFile imageFile;
}
