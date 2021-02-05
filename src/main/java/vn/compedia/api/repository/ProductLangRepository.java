package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.ProductLang;

import java.util.List;

@Repository
public interface ProductLangRepository extends JpaRepository<ProductLang, Long> {
    List<ProductLang> findAllByProductId(Long id);

    ProductLang findAllByProductIdAndLanguageId(Long id, Integer languageId);

    ProductLang findAllByProductLangId(Long id);
}
