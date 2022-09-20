package vn.compedia.api.repository;


import vn.compedia.api.response.FunctionRoleResponse;

import java.util.List;


public interface FunctionRoleRepositoryCustom {
    List<FunctionRoleResponse> findAll(Integer roleId);


}
