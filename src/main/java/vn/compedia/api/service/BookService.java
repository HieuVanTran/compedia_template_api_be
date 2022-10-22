package vn.compedia.api.service;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.entity.Book;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.request.BookCreateRequest;
import vn.compedia.api.response.BookAmountCallCardResponse;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.utility.FileUtil;

import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CallCardRepository callCardRepository;

    public List<BookResponse> getAll() {
        List<BookResponse> list = bookRepository.getAllBook();
        return list;
    }

    public Book getOne(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            book = new Book();
        }
        return book;
    }

    public void validateData(BookCreateRequest request) throws Exception {

        if (StringUtils.isBlank(request.getBookName().trim())) {
            throw new Exception("Không được để trống BookName!");
        }
        if (request.getBookName().trim().length() > 50) {
            throw new Exception("Vượt quá 50 ký tự, yêu cầu nhập lại fullName");
        }
        if (StringUtils.isBlank(request.getPublishingYear().trim())) {
            throw new Exception("Không được để trống PublishingYear!");
        }
        if (StringUtils.isBlank(request.getNote().trim())) {
            throw new Exception("Không được để trống Note!");
        }
        if (request.getNote().trim().length() > 16777215) {
            throw new Exception("Vượt quá  ký tự cho phép");
        }
    }

    public void create(BookCreateRequest request, MultipartFile file) throws Exception {
        validateData(request);
        Book book = new Book();
        book.setBookName(request.getBookName());
        book.setAmount(request.getAmount());
        book.setIdAuthor(request.getIdAuthor());
        book.setIdTypeBook(request.getIdTypeBook());
        book.setCompanyId(request.getCompanyId());
        book.setPageNumber(request.getPageNumber());
        book.setPrice(request.getPrice());
        book.setNote(request.getNote());
        book.setImage(FileUtil.saveImage(file));
        book.setPublishingYear(request.getPublishingYear());
        book.setStatus(DbConstant.STATUS_ACTIVE);
        bookRepository.save(book);
    }

    public void update(BookCreateRequest request, MultipartFile file) throws Exception {
        validateData(request);
        Book book = bookRepository.findById(request.getId()).get();
        book.setBookName(request.getBookName());
        book.setAmount(request.getAmount());
        book.setIdAuthor(request.getIdAuthor());
        book.setIdTypeBook(request.getIdTypeBook());
        book.setCompanyId(request.getCompanyId());
        book.setPrice(request.getPrice());
        book.setImage(FileUtil.saveImage(file));
        book.setPublishingYear(request.getPublishingYear());
        book.setNote(request.getNote());
        book.setPageNumber(request.getPageNumber());
        book.setStatus(1);
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public Page<BookResponse> search(String bookName, Long categoryId, Long authorId, Long publishId,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return bookRepository.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }

    public void updateAmount(BookAmountCallCardResponse response, Long bookId, Integer amount) throws Exception {
        validateData(response);
        CallCard callCard = callCardRepository.findById(response.getCallCardID()).get();
        if (response.getType() == 1) {
            callCard.setStatus(DbConstant.STATUS_APPROVED);
            callCardRepository.save(callCard);
            bookRepository.updateSubtractAmount(bookId,amount);
        } else if (response.getType() == 2) {
            callCard.setIsAction(DbConstant.ACTION_PAID);
            callCardRepository.save(callCard);
            bookRepository.updateSumAmount(bookId,amount);
        } else if (response.getType() == 3) {
            callCard.setIsAction(DbConstant.ACTION_TRANSGRESS);
            callCardRepository.save(callCard);
        }
    }
    public void validateData(BookAmountCallCardResponse response) throws Exception {


        // Check exit book
        Optional<Book> bookOptional = bookRepository.findById(response.getBookID());
        if (!bookOptional.isPresent()) {
            throw new Exception("Don't exit book with id :" + response.getBookID());
        }
        // Check quantity book compare with quantity call card
        if (bookOptional.get().getAmount() > 0 && bookOptional.get().getAmount() >= response.getAmount()) {
            throw new Exception("Don't quantity book with quantity call card");
        }
    }
}



