package vn.compedia.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.BookResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Bidi;
import java.util.List;

public interface BookRepositoryCustom {

    Page<BookResponse> search(String bookName, String authorName, String categoryName, String publishName,
                              String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<BookResponse> getAllBook();

    BigDecimal getTotalBook();


}
