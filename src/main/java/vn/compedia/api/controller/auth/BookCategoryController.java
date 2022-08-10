package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Author;
import vn.compedia.api.entity.BookCategory;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.BookCategoryCreateRequest;
import vn.compedia.api.service.BookCategoryService;

import java.util.List;

@Api(tags = "BookCategory")
@RestController
@RequestMapping("/api/v1/book-category")
@Validated

public class BookCategoryController extends GlobalExceptionHandler {
    @Autowired
    private BookCategoryService bookCategoryService;

    @GetMapping(value = "book-category")
    public ResponseEntity<?> getAll() {
        List<BookCategory> list = bookCategoryService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }
    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long idtypeBook) {
        BookCategory bookCategory = bookCategoryService.getOne(idtypeBook);
        return VietTienResponseDto.ok(bookCategory, "Get list account success");
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BookCategoryCreateRequest request) {
        bookCategoryService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody BookCategoryCreateRequest request) {
        bookCategoryService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id ){
        bookCategoryService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}

