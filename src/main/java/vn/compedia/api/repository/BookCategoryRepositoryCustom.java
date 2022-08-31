package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.BookCategoryResponse;

public interface BookCategoryRepositoryCustom {
    Page<BookCategoryResponse> search(String categoryName, String bookName, String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

}
