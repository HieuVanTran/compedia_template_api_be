package vn.compedia.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.util.MessageUtil;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "book")
public class Book  {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    @Size(max = 50,message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String bookName;

    @Column(name = "publishing_year")
    private String publishingYear;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Integer price;

    @Column(name = "id_type_book")
    private Long idTypeBook;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "amount")
    private Integer amount;

    // 0: không có giá trị ? 1 giá trị
    @Column(name = "status")
    private Integer status;

    @Column(name = "id_author")
    private Long idAuthor;


}
