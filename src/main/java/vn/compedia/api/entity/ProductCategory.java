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
@Table(name = "product_category")
public class ProductCategory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    @JsonProperty("product_category_id")
    private Long productCategoryId;

    @Column(name = "status")
    @JsonProperty("status")
    private Integer status;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "name_vi")
    @JsonProperty("name_vi")
    private String nameVi;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "description_vi")
    @JsonProperty("description_vi")
    private String descriptionVi;

    @Column(name = "image_path")
    @JsonProperty("image_path")
    private String imagePath;

    @Column(name = "tag")
    @JsonProperty("tag")
    private Integer tag;

    @Column(name = "parent_id")
    @JsonProperty("parent_id")
    private Long parentId;

}
