package vn.compedia.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.entity.Role;
import vn.compedia.api.response.FunctionRoleResponse;

import java.util.List;


public interface FunctionRoleRepositoryCustom {
    List<FunctionRoleResponse> findAll(Integer roleId);


//    Page<Role> search(String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);
}
