package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Author;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.AuthorCreateRequest;
import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.service.AuthorService;

import java.util.List;


@Api(tags = "Author")
@RestController
@RequestMapping("/api/v1/author")
@Validated

public class AuthorController extends GlobalExceptionHandler {
    @Autowired
    private AuthorService authorService;


    @GetMapping(value = "author")
    public ResponseEntity<?> getAll() {
        List<AuthorResponse> list = authorService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long idAuthor) {
        Author author = authorService.getOne(idAuthor);
        return VietTienResponseDto.ok(author, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "authorName", required = false) String authorName,
                                    @RequestParam(name = "address", required = false) String address,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder,
                                    @RequestParam(name = "title", required = false) String title) {
        Page<AuthorResponse> list = authorService.search(authorName, address, title, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AuthorCreateRequest request) {
        try {
            authorService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody AuthorCreateRequest request) {
        try {
            authorService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }


    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        authorService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}

