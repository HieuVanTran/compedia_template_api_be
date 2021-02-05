package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.compedia.api.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Query("select t from ProductCategory t where t.status = :status")
    List<ProductCategory> getAllByStatus(@Param("status") Integer status);
}
