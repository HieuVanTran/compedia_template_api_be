package vn.compedia.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "new_staff")
public class NewStaff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_staff_id")
    private Long id;

    @Column(name = "code_staff")
    private String codeStaff;

    @Column(name = "name_staff")
    private String nameStaff;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "sex")
    private String sex;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "position_id")
    private String position_id;

    @Column(name = "department_id")
    private String department_id;

    @Column(name = "status")
    private int status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_by")
    private String createBy;
}
