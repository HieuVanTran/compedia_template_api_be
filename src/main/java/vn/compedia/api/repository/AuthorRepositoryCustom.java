package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.AuthorResponse;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepositoryCustom {

    List<AuthorResponse> getAllAuthor();

    Page<AuthorResponse> search(String authorName, String title, String address,
                                String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    BigInteger getTotalAuthor();
}
