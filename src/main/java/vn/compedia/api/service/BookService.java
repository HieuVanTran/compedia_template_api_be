package vn.compedia.api.service;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.entity.Book;
import vn.compedia.api.repository.BookRepository;
import vn.compedia.api.request.BookCreateRequest;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.utility.FileUtil;

import java.util.List;


@Log4j2
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

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
//        book.setImage(FileUtil.saveImage(MultipartFile (uploadedFile.getInputStream()));
//        Date pby = DateUtil.formatDatePattern(request.getPublishingYear(), DateUtil.DATE_FORMAT_YEAR);
//        book.setPublishingYear(pby);
//        book.setImage(FileUtil.saveImage(file.getOriginalFilename());
        book.setImage(FileUtil.saveImage(file));
//        book.setPublishName(FileUtil.saveFiles(request.getImage()));
//        Image img = FileUtil.saveImage(file.getOriginalFilename(), FileUtil.);
//        book.setImage(img);
        book.setPublishingYear(request.getPublishingYear());
        book.setStatus(1);
        bookRepository.save(book);
    }

    public void update(BookCreateRequest request, MultipartFile file) throws Exception {
        validateData(request);
        Book book = new Book();
        book.setBookId(request.getId());
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
}

