package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.AuthorRepository;
import vn.compedia.api.repository.BookCategoryRepository;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeAuthorResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomePageResponse;

import java.util.List;

@Log4j2
@Service

public class HomeCategoryService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<HomePageResponse> getAll() {
        return bookRepository.findAllHome();

    }

    public List<HomeCategoryResponse> getAllCategory( ) {

        return bookCategoryRepository.findByCategory();
    }
    public List<HomeAuthorResponse> getAllAuthor(  ) {

        return authorRepository.findByAuthor();
    }


    public Page<BookResponse> search(String bookName, Long categoryId, Long authorId, Long publishId,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}
