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
import vn.compedia.api.response.index.HomePageResponse;
import vn.compedia.api.service.HomePageService;

import java.util.List;

@Api(tags = "Fe-PageController")
@RestController
@RequestMapping("/api/v1/home-page")
@Validated

public class HomePageController extends GlobalExceptionHandler {

    @Autowired
    private HomePageService homePageService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        List<HomePageResponse> list = homePageService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "idTypeBook") Long idTypeBook) {
        try {
            List<HomePageResponse> home = homePageService.getOne(idTypeBook);
            return VietTienResponseDto.ok(home, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
//        List<HomePageResponse> home = homePageService.getOne(idTypeBook);
//        return VietTienResponseDto.ok(home, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "category_name", required = false) String categoryName,
                                    @RequestParam(name = "name_author", required = false) String nameAuthor,
                                    @RequestParam(name = "publish_name", required = false) String publishName,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sort_field", required = false) String sortField,
                                    @RequestParam(name = "sort_order", required = false) String sortOrder) {
        Page<BookResponse> list = homePageService.search(bookName, nameAuthor, categoryName, publishName, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }
}
