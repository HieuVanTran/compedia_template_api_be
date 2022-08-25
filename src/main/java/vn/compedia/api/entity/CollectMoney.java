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
@Table(name = "collect_money")
public class CollectMoney  {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "collect_money_id")
    private Long collectMoneyId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    @Size(max = 50,message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
    private String fullName;

    @Column(name = "fined_amount")
    private Double finedAmount;

    @Column(name = "proceeds")
    private Double proceeds;

    @Column(name = "staff_id")
    private Integer staffId;
}
