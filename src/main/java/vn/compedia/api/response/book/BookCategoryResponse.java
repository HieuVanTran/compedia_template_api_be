package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCategoryResponse {

    @JsonProperty("id_type_book")
    private Long idTypeBook;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("book_name")
    private String bookName;


}
