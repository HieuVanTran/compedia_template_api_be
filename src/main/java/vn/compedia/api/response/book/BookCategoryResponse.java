package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCategoryResponse {

    private Long idTypeBook;

    private String categoryName;


}
