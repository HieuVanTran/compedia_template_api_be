package vn.compedia.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_card_details")
public class CallCardDetails {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "call_card_details_id")
    private Long callCardDetailsId;

    @Column(name = "call_card_id")
    private Long callCardId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "amount")
    private Integer amount;
}
