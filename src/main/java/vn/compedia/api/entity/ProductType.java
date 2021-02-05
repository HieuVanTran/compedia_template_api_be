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
@Table(name = "product_type")
public class ProductType implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_type_id")
	@JsonProperty("product_type_id")
	private Long productTypeId;

	@Column(name = "product_id")
	@JsonProperty("product_id")
	private Long productId;

	@Column(name = "type_name")
	@JsonProperty("type_name")
	private String typeName;

}
