package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.ProductImages;

import java.util.List;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
   List<ProductImages> findAllByProductId(Long id);
}
