package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.NewStaffResponse;

public interface NewStaffRepositoryCustom {

    Page<NewStaffResponse> search(String keyword, String sortField, String sortOrder, Integer page,
                                  Integer size, Pageable pageable);
}
