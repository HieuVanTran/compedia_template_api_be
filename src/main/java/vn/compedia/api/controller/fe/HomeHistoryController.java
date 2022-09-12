package vn.compedia.api.controller.fe;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.response.HomeHistoryResponse;
import vn.compedia.api.service.HomeHistoryService;

import java.util.List;

@Api(tags = "Fe-HomeHistoryController")
@RequestMapping("/api/v1/home-history")
@RestController
@Validated
public class HomeHistoryController {

    @Autowired
    private HomeHistoryService homeHistoryService;

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

}