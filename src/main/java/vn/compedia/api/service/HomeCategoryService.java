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
import java.util.Optional;

@Log4j2
@Service

public class HomeCategoryService {
    @Autowired
    private BookRepository bookRepository;

    public List<HomePageResponse> getAll() {
        return bookRepository.findAllHome();
    }

//
//    public HomePageResponse getOne(Long idTypeBook, Long idAuthor  ) throws Exception {
//        Optional<HomePageResponse> category = bookRepository.findByCategory(idTypeBook, idAuthor);
//        if (!category.isPresent()) {
//            throw new Exception(" EMPTY ");
//        }
//        return category.get();

    public List<HomeCategoryResponse> getOne(Long idTypeBook, Long idAuthor  )  {
        return bookRepository.findByCategory(idTypeBook, idAuthor);
    }


    public Page<BookResponse> search(String bookName, String nameAuthor, String categoryName, String publishName,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, nameAuthor, categoryName, publishName, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}