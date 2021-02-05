package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "customer")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @JsonProperty("customer_id")
    private Long customerId;

    @Column(name = "name")
    @JsonProperty("name")
    @Size(max = 50)
    private String name;

    @Column(name = "phone")
    @Size(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
    private String phone;

    @Column(name = "email")
    @Size(max = 50, message = MessageUtil.EMAIL_HAS_MAX_LENGTH)
    private String email;

    @Column(name = "facebook")
    @Size(max = 200, message = MessageUtil.FACEBOOK_HAS_MAX_LENGTH)
    private String facebook;

    @Column(name = "logo")
    @Size(max = 200)
    private String logo;

    @Column(name = "description")
    private String description;

    @Column(name = "province_id")
    @JsonProperty("province_id")
    private Long provinceId;

    @Column(name = "district_id")
    @JsonProperty("district_id")
    private Long districtId;

    @Column(name = "commune_id")
    @JsonProperty("commune_id")
    private Long communeId;

    @Column(name = "address")
    @JsonProperty("address")
    private String address;

    @Column(name = "customer_role_id")
    @JsonProperty("customer_role_id")
    private Long customerRoleId;

    @Column(name = "status")
    private Integer status;
}
