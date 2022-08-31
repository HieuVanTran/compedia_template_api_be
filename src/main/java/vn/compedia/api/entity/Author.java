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

@Table(name = "author")

public class Author {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    private Long idAuthor;

    @Column(name = "name_author")
    @Size(max = 50, message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String nameAuthor;

    @Column(name = "address")
    @Size(max = 100, message = MessageUtil.ADDRESS_HAS_MAX_LENGTH)
    private String address;

    @Column(name = "title")
    private String title;

    @Column(name = "note")
    private String note;


}
