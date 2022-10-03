package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Book;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.BookCreateRequest;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.service.BookService;

import java.util.List;

@Api(tags = "DetailBook")
@RestController
@RequestMapping("/api/v1/book")
@Validated

public class DetailBookController extends GlobalExceptionHandler {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/book")
    public ResponseEntity<?> getAll() {
        List<BookResponse> list = bookService.getAll();
        return VietTienResponseDto.ok(list, "Get list book success");
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "categoryId", required = false) Long categoryId,
                                    @RequestParam(name = "authorId", required = false) Long authorId,
                                    @RequestParam(name = "publishId", required = false) Long publishId,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder) {
        Page<BookResponse> list = bookService.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @GetMapping(value = "/get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long bookId) {
        Book book = bookService.getOne(bookId);
        return VietTienResponseDto.ok(book, "Get list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(BookCreateRequest request, MultipartFile file) {
        try {
            bookService.create(request, file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(BookCreateRequest request, MultipartFile file) {
        try {
            bookService.update(request, file);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        bookService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }

}

