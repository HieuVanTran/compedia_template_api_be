package vn.compedia.api.controller.fe;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomePageResponse;
import vn.compedia.api.service.HomeCategoryService;

import java.util.List;

@Api(tags = "Fe-CategoryController")
@RequestMapping("/api/v1/home-category")
@RestController
@Validated

public class HomeCategoryController extends GlobalExceptionHandler {

    @Autowired
    private HomeCategoryService homeCategoryService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<HomePageResponse> list = homeCategoryService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "idTypeBook", required = false) Long idTypeBook,
                                    @RequestParam(name = "idAuthor", required = false) Long idAuthor) {
        List<HomeCategoryResponse> home = homeCategoryService.getOne(idTypeBook, idAuthor);
        return VietTienResponseDto.ok(home, "Get list account success");
    }

    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "categoryId", required = false) Long categoryId,
                                    @RequestParam(name = "authorId", required = false) Long authorId,
                                    @RequestParam(name = "publishId", required = false) Long publishId,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder) {
        Page<BookResponse> list = homeCategoryService.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }
    }
