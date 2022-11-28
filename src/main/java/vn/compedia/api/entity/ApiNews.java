package vn.compedia.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "table_name")
public class ApiNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private Long email;

    @Column(name = "address")
    private String address;
}
