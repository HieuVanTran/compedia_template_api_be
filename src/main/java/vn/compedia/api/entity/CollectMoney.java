package vn.compedia.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.util.MessageUtil;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "collect_money")
public class CollectMoney extends BaseEntity {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

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
