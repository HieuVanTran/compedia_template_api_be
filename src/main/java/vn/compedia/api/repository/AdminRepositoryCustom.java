package vn.compedia.api.repository;

import vn.compedia.api.response.admin.AdminResponse;

import java.util.List;

public interface AdminRepositoryCustom {
     List<AdminResponse> search(String username, String email, String fullName, String roleId);



}


