package vn.compedia.api.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomePageResponse;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service

public class HomePageService {

    @Autowired
    private BookRepository bookRepository;

    public List<HomePageResponse> getAll() {
        return bookRepository.findAllHome();
    }

    public List<HomePageResponse> getOne(Long idTypeBook) throws Exception {
        List<HomePageResponse> home = bookRepository.findByHome(idTypeBook);
        if (home.isEmpty()) {
            throw new Exception(" EMPTY ");
        }
        return home;
    }
    public Page<BookResponse> search(String bookName, Long categoryId, Long authorId, Long publishId,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}


