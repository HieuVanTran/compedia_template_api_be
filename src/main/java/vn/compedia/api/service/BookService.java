package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Book;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.request.BookCreateRequest;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.util.DateUtil;

import java.util.Date;
import java.util.List;


@Log4j2
@Service
public class BookService {
    @Value("${vn.compedia.static.context}")
    private String staticContext;
    @Value("${accept_image_file_types}")
    private String acceptImageTypes;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAll() {
        List<Book> list = bookRepository.findAll();
        return list;
    }

    public Book getOne(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            book = new Book();
        }
        return book;
    }

    public void create(BookCreateRequest request) {
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setAmount(request.getAmount());
        book.setIdAuthor(request.getIdAuthor());
        book.setIdCompany(request.getIdCompany());
        book.setIdTypeBook(request.getIdTypeBook());
        book.setPageNumber(request.getPageNumber());
        book.setPrice(request.getPrice());
        book.setImage(request.getImage());
        Date pby = DateUtil.formatDatePattern(request.getPublishingYear(), DateUtil.DATE_FORMAT_YEAR);
        book.setPublishingYear(pby);
        book.setStatus(request.getStatus());
        bookRepository.save(book);
    }

    public void update(BookCreateRequest request) {
        Book book = new Book();
        book.setBookId(request.getId());
        book.setBookName(request.getBookName());
        book.setAmount(request.getAmount());
        book.setIdAuthor(request.getIdAuthor());
        book.setIdCompany(request.getIdCompany());
        book.setIdTypeBook(request.getIdTypeBook());
        book.setPageNumber(request.getPageNumber());
        book.setPrice(request.getPrice());
        book.setImage(request.getImage());
        Date pby = DateUtil.formatDatePattern(request.getPublishingYear(), DateUtil.DATE_FORMAT_YEAR);
        book.setPublishingYear(pby);
        book.setStatus(request.getStatus());
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponse> search(String bookName, String authorName) {
        return bookRepository.search(bookName, authorName);
    }
}

