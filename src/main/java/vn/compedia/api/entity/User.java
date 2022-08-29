package vn.compedia.api.entity;


import lombok.*;

import javax.persistence.*;

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
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "create_date")
    private String createDate;
}
