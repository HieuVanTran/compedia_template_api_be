package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.user.UserResponse;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserResponse> getAllUser();

    Page<UserResponse> search(String fullName, String address, String phone,
                              String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

}
