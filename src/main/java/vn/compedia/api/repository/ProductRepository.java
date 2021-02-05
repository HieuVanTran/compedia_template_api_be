package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query("select pr from Product pr where pr.code = :code and pr.status = 1 ")
    List<Product> findAllProductByCodeAndStatus(@Param("code") String code);

    List<Product> findByCode(String code);

    Product findByProductId(Long id);
}
