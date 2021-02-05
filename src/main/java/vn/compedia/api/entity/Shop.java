package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shop")
public class Shop extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    @JsonProperty("shop_id")
    private Long shopId;

    @Column(name = "account_id")
    @JsonProperty("account_id")
    private Long accountId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "facebook")
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
    @Size(max = 100)
    private String address;

    @Column(name = "status")
    private Integer status;
}
