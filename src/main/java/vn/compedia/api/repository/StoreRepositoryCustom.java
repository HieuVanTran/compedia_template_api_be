package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.product.WarehouseResponse;

import java.util.Map;

public interface StoreRepositoryCustom {
    Page<WarehouseResponse> searchWarehouse(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable, Integer languageId);
}
