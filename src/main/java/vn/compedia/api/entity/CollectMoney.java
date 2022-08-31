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
@Table(name = "collect_money")
public class CollectMoney {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collect_money_id")
    private Long collectMoneyId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "fined_amount")
    private Double finedAmount;

    @Column(name = "proceeds")
    private Double proceeds;

    @Column(name = "staff_id")
    private Integer staffId;
}
