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
@Table(name = "product_lang")
public class ProductLang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_lang_id")
    @JsonProperty("product_lang_id")
    private Long productLangId;

    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long productId;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "wholesale_prices")
    @JsonProperty("wholesale_prices")
    private Double wholesalePrices;

    @Column(name = "retail_price")
    @JsonProperty("retail_price")
    private Double retailPrice;

    @Column(name = "language_id")
    private Integer languageId;

}
