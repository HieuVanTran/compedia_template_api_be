package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long productId;

    @Column(name = "product_category_id")
    @JsonProperty("product_category_id")
    private Long productCategoryId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "name_vi")
    private String nameVi;

    @Column(name = "description")
    private String description;

    @Column(name = "description_vi")
    private String descriptionVi;

    @Column(name = "images")
    private String images;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    @JsonProperty("price")
    private Double price;

    @Column(name = "price_import")
    @JsonProperty("price_import")
    private Double priceImport;

    @Column(name = "status")
    private Integer status;

}
