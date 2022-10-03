package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.response.BookAmountCallCardResponse;
import vn.compedia.api.service.BookService;


@Api(tags = "Api-Amount-Book")
@RestController
@RequestMapping("/api/v1/amount-book")
@Log4j2
@Validated


public class ApiAmountBook {

    @Autowired
    private BookService bookService;

    @PutMapping()
    public ResponseEntity<?> updateAmount(BookAmountCallCardResponse response, Long bookId, Integer amount) {
        try {
            bookService.updateAmount(response, bookId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }
}
