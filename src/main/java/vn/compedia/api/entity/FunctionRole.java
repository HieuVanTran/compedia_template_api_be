package vn.compedia.api.entity;


import io.swagger.models.auth.In;
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
@Table(name = "function_role")


public class FunctionRole {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "function_role_id")
    private Long functionRoleId;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "function")
    private String function;

    @Column(name = "status")
    private Integer status;

}
