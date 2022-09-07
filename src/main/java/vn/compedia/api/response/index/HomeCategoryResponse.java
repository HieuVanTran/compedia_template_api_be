package vn.compedia.api.response.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HomeCategoryResponse {


    @JsonProperty("id_type_book")
    private Long idTypeBook;

    @JsonProperty("category_name")
    private String categoryName;


}
