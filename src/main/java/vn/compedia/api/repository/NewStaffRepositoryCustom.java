package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.PositionsDepartmentResponse;

public interface NewStaffRepositoryCustom {

    Page<PositionsDepartmentResponse> search(String keyword, String positionName, String departmentName, String sortField,
                                             String sortOrder, Pageable pageable);

}
