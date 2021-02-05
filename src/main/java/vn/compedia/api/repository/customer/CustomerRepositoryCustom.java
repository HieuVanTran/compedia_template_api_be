package vn.compedia.api.repository.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.ShopManage.ShopListResponse;

import java.util.Map;

public interface CustomerRepositoryCustom {
    Page<ShopListResponse> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable);
}
