package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCategoryCreateRequest {


    private Long Id;

    @JsonProperty("book_name")
    private String bookName;

    private String code;


}
