package vn.compedia.api.controller.fe;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import vn.compedia.api.response.index.HomeDetailsResponse;
import vn.compedia.api.service.HomeDetailsService;

import java.util.List;

@Api(tags = "Fe-DetailsController")
@RequestMapping("/api/v1/home-details")
@RestController
@Validated
public class HomeDetailsController extends GlobalExceptionHandler {

    @Autowired
    private HomeDetailsService homeDetailsService;

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "book_id") Long bookId) {
        try {
            HomeDetailsResponse details = homeDetailsService.getOne(bookId);
            return VietTienResponseDto.ok(details, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "get-popular")
    public ResponseEntity<?> getByPopular(@RequestParam(name = "bookId") Long bookId) {
        try {
            List<HomeDetailsResponse> popular = homeDetailsService.getByPopular(bookId);
            return VietTienResponseDto.ok(popular, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "categoryId", required = false) Long categoryId,
                                    @RequestParam(name = "authorId", required = false) Long authorId,
                                    @RequestParam(name = "publishId", required = false) Long publishId,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder) {
        Page<BookResponse> list = homeDetailsService.search(bookName, categoryId, authorId, publishId, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }
}