package vn.compedia.api.repository;


import vn.compedia.api.response.book.BookResponse;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookResponse> search(String bookName, String authorName);

}
