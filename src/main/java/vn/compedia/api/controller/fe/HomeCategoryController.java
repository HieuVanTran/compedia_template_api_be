package vn.compedia.api.controller.fe;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomePageResponse;
import vn.compedia.api.service.HomeCategoryService;
import vn.compedia.api.service.HomePageService;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> getOne(@RequestParam(name = "idTypeBook",required = false) Long idTypeBook,
                                    @RequestParam(name = "idAuthor",required = false)   Long idAuthor ) {
//        try {
//            HomePageResponse home = homeCategoryService.getOne(idTypeBook,idAuthor);
//            return VietTienResponseDto.ok(home, "Get list account success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
        List<HomeCategoryResponse> home = homeCategoryService.getOne(idTypeBook,idAuthor);
        return VietTienResponseDto.ok(home, "Get list account success");
    }@GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "bookName", required = false) String bookName,
                                    @RequestParam(name = "category_name", required = false) String categoryName,
                                    @RequestParam(name = "name_author", required = false) String nameAuthor,
                                    @RequestParam(name = "publish_name", required = false) String publishName,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name ="sort_field", required = false) String sortField,
                                    @RequestParam(name ="sort_order", required = false) String sortOrder) {
        Page<BookResponse> list = homeCategoryService.search(bookName, nameAuthor,categoryName,publishName,sortField,sortOrder,page,size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }
}