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
@Table(name = "product_images")
public class ProductImages implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_images_id")
	@JsonProperty("product_images_id")
	private Long ProductImagesId;

	@Column(name = "product_id")
	@JsonProperty("product_id")
	private Long productId;

	@Column(name = "image_path")
	@Size(max = 300)
	@JsonProperty("image_path")
	private String imagePath;

}
