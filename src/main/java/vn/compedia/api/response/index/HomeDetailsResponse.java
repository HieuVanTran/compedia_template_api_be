package vn.compedia.api.response.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HomeDetailsResponse {
    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("id_author")
    private Long idAuthor;

    @JsonProperty("name_author")
    private String nameAuthor;

    @JsonProperty("id_type_book")
    private Long idTypeBook;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("publish_name")
    private String publishName;

    @JsonProperty("image")
    private String image;

    @JsonProperty("note")
    private String note;

    @JsonProperty("publishing_year")
    private String publishingYear;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("status")
    private Integer status;


}
