package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface AbstractExtRepository<T> {
    Page<T> search(Map<String, Object> filters, Pageable pageable);

    Page<T> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable);
}
