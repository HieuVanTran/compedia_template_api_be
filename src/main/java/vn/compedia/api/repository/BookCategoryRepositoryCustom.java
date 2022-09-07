package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.BookCategoryResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;

import java.util.List;

public interface BookCategoryRepositoryCustom {
    Page<BookCategoryResponse> search(String categoryName , String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<HomeCategoryResponse> findByCategory();
}
