package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.admin.AdminResponse;

public interface AdminRepositoryCustom {
     Page<AdminResponse> search(String username, String email, Integer roleId,String codeRole, String fullName,
                                String sortField,String sortOrder, Integer page, Integer size, Pageable pageable);



}


