package vn.compedia.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeAuthorResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomeDetailsResponse;
import vn.compedia.api.response.index.HomePageResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {

    Page<BookResponse> search(String bookName, Long authorId, Long categoryId, Long publishId,
                              String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<BookResponse> getAllBook();

    BigDecimal getTotalBook();

    List<HomePageResponse> findAllHome();

    List<HomePageResponse> findByHome(Long idTypeBook);

    List<HomeCategoryResponse> findByCategory(Long idTypeBook);

    List<HomeAuthorResponse> findByAuthor(Long idAuthor);

    Optional<HomeDetailsResponse> findByDetails(Long bookId);

    List<HomeDetailsResponse> findByListDetails(Long bookId);
}
