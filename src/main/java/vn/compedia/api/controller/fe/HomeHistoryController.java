package vn.compedia.api.controller.fe;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.response.HomeHistoryResponse;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.service.CallCardService;
import vn.compedia.api.service.HomeHistoryService;

import java.util.List;

@Api(tags = "Fe-HomeHistoryController")
@RequestMapping("/api/v1/home-history")
@RestController
@Validated
public class HomeHistoryController {

    @Autowired
    private HomeHistoryService homeHistoryService;

    @Autowired
    private CallCardService callCardService;

    @GetMapping(value = "get-list-book")
    public ResponseEntity<?> getAllHistory() {
        try {
            List<HomeHistoryResponse> history = homeHistoryService.getAllHistory();
            return VietTienResponseDto.ok(history, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "username", required = false) String username,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder
                                  ) {
        PageImpl<Object> list = homeHistoryService.search(username, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

}