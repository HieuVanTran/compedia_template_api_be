package vn.compedia.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookCreateRequest {

    private Long Id;

    private String bookName;

    private Long idAuthor;

    private String publishingYear;

    private Integer pageNumber;

    private String image;

    private Integer price;

    private Long idTypeBook;

    private Long companyId;

    private Integer amount;

    private Integer status;

    private String note;


}
