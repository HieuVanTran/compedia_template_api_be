package vn.compedia.api.repository;

import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.book.UserResponse;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserResponse> search(String fullName);

}
