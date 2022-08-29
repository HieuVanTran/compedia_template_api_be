package vn.compedia.api.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeDetailsResponse;

import java.util.Optional;

@Log4j2
@Service

public class HomeDetailsService {

    @Autowired
    private BookRepository bookRepository;

    public HomeDetailsResponse getOne(Long bookId  ) throws Exception {
        Optional<HomeDetailsResponse> details = bookRepository.findByDetails(bookId);
        if (!details.isPresent()) {
            throw new Exception(" EMPTY ");
        }
        return details.get();
    }
    public Page<BookResponse> search(String bookName, String nameAuthor, String categoryName, String publishName,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, nameAuthor,categoryName,publishName,sortField,sortOrder,page,size, PageRequest.of(page, size));
    }
}

