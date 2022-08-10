package vn.compedia.api.repository;

import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.response.book.BookResponse;

import java.util.List;

public interface AuthorRepositoryCustom {

    List<AuthorResponse> search( String authorName, String title);

}
