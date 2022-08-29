package vn.compedia.api.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomePageResponse;

import java.util.List;

@Log4j2
@Service

public class HomePageService {

    @Autowired
    private BookRepository bookRepository;

    public List<HomePageResponse> getAll() {
        return bookRepository.findAllHome();
    }

    public List<HomePageResponse> getOne( Long idTypeBook  ) throws Exception {
        List<HomePageResponse> home = bookRepository.findByHome(idTypeBook);
        if ( home.isEmpty()) {
            throw new Exception(" EMPTY ");
        }
        return home;
    }
//    public List<HomePageResponse> getOne(Long idTypeBook) {
//        return bookRepository.findByHome(idTypeBook);
//    }

    public Page<BookResponse> search(String bookName, String nameAuthor, String categoryName, String publishName,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, nameAuthor, categoryName, publishName, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}



