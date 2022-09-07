package vn.compedia.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.admin.StaffResponse;

import java.util.List;

public interface StaffRepositoryCustom {
    Page<StaffResponse> search(Long staffId, String phoneNumber, String address,
                               String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<StaffResponse> getAllStaff();
}
