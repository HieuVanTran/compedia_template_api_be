package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

    @JsonProperty("images")
    private String images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_vi")
    private String nameVi;

    @JsonProperty("code")
    private String code;

    @JsonProperty("type_name")
    private String typeName;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("price")
    private Double price;
}
