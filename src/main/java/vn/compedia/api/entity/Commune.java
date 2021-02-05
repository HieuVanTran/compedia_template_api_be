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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commune")
public class Commune implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commune_id")
    @JsonProperty("commune_id")
    private Long communeId;

    @Column(name = "name")
    @Size(max = 100)
    private String name;

    @Column(name = "district_id")
    @JsonProperty("district_id")
    private Long districtId;

}
