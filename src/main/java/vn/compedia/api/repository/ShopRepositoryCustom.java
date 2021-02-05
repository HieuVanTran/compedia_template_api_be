package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.shop.ShopResponse;

import java.util.Map;

public interface ShopRepositoryCustom {
    Page<ShopResponse> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable);
}
