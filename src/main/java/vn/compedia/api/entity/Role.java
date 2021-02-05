package vn.compedia.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1674924613078177003L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Override
    public String getAuthority() {
        return name;
    }

}
