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
@Table(name = "product_size")
public class ProductSize implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_size_id")
	@JsonProperty("product_size_id")
	private Long productSizeId;

	@Column(name = "product_id")
	@JsonProperty("product_id")
	private Long productId;

	@Column(name = "name_size")
	@JsonProperty("name_size")
	private String nameSize;

	@JsonProperty("quantity_in_stock")
	@Column(name = "quantity_in_stock")
	private String quantityInStock;

}
