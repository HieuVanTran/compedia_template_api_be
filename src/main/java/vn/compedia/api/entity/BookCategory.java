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
@Table(name = "book_category")
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_book")
    private Long idtypeBook;

    @Column(name = "code")
    private String code;

    @Column(name = "book_name")
    @Size(max = 50,message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String bookName;





}
