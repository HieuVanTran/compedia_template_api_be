package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.dto.OrdersDto;

import java.util.Map;

public interface OrdersRepositoryCustom {
    Page<OrdersDto> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable);

}
