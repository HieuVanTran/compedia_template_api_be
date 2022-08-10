package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "book")
    public ResponseEntity<?> getAll() {
        List<Book> list = bookService.getAll();
        return VietTienResponseDto.ok(list, "Get list book success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "authorName", required = false) String authorName) {
        List<BookResponse> list = bookService.search(bookName, authorName);
        return VietTienResponseDto.ok(list, "Search list book success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long bookId) {
        Book book = bookService.getOne(bookId);
        return VietTienResponseDto.ok(book, "Get list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookCreateRequest request) {
        bookService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody BookCreateRequest request) {
        bookService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        bookService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }

}

