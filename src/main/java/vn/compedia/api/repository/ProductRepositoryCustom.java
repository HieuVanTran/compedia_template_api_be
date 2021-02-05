package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.product.ProductResponse;

import java.util.Map;

public interface ProductRepositoryCustom {
    Page<ProductResponse> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable, Integer languageId);

}
