package vn.compedia.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_card")

public class CallCard {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_card_id")
    private Long callCardId;

//    @Column(name = "username")
//    private String userName;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "note")
    private String note;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "amount")
    private Integer amount;


}
