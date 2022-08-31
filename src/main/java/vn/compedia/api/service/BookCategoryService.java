package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.BookCategory;
import vn.compedia.api.repository.BookCategoryRepository;
import vn.compedia.api.request.BookCategoryCreateRequest;
import vn.compedia.api.response.book.BookCategoryResponse;

import java.util.List;

@Log4j2
@Service
public class BookCategoryService {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;


    public List<BookCategory> getAll() {
        List<BookCategory> list = bookCategoryRepository.findAll();
        return list;
    }

    public BookCategory getOne(Long idtypeBook) {
        BookCategory bookCategory = bookCategoryRepository.findById(idtypeBook).orElse(null);
        if (bookCategory == null) {
            bookCategory = new BookCategory();
        }
        return bookCategory;
    }

    public void create(BookCategoryCreateRequest request) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setCategoryName(request.getCategoryName());
        bookCategoryRepository.save(bookCategory);
    }

    public void update(BookCategoryCreateRequest request) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setIdTypeBook(request.getId());
        bookCategory.setCategoryName(request.getCategoryName());
        bookCategoryRepository.save(bookCategory);
    }

    public void delete(Long id) {

        bookCategoryRepository.deleteById(id);
    }

    public Page<BookCategoryResponse> search(String categoryName, String bookName, String sortField, String sortOrder, Integer page, Integer size) {
        return bookCategoryRepository.search(categoryName, bookName, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}