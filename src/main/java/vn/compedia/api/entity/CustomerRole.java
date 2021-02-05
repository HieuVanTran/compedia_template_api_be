package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_role")
public class CustomerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_role_id")
    @JsonProperty("customer_role_id")
    private Long customerRoleId;

    @Column(name = "name")
    @JsonProperty("name")
    @Size(max = 200)
    private String name;

    @Column(name = "name_vi")
    @JsonProperty("name_vi")
    @Size(max = 200)
    private String nameVi;

    @Column(name = "discount")
    @JsonProperty("discount")
    private Double discount;

    @Column(name = "status")
    private Integer status;
}
