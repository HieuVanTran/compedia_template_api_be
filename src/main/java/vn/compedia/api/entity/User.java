package vn.compedia.api.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "full_name")
    private String  fullName;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "address")
    private String  address;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "card_number")
    private String  cardNumber;

    @Column(name = "account_id")
    private Long accountId;

}
