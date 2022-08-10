package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class BookCreateRequest {

    private  Long Id;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("id_author")
    private Long idAuthor;

    @JsonProperty("publishing_year")
    private  String publishingYear;

    @JsonProperty("page_number")
    private Integer pageNumber;

    private String image;

    private Integer price;

    @JsonProperty("id_type_book")
    private Long idTypeBook;

    @JsonProperty("id_company")
    private Long idCompany;

    private Integer amount;

    private Integer status;



}
