package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.exception.datevalidator.ApiDateFormat;
import vn.compedia.api.response.DashBoardResponse;
import vn.compedia.api.response.MonthDataResponse;
import vn.compedia.api.response.index.HomeDetailsResponse;
import vn.compedia.api.service.DashBoardService;

import java.util.List;

@Api(tags = "Dashboard")
@RestController
@RequestMapping("/api/v1/dashboard")
@Validated
public class DashBoardController extends GlobalExceptionHandler {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        DashBoardResponse response = dashBoardService.getDate();
        return VietTienResponseDto.ok(response, "Get data dashboard success");
    }
    @GetMapping(value = "get-one")
    public ResponseEntity<?> getMonth(@RequestParam(name = "Year") String year) {
        try {
            List<MonthDataResponse> month = dashBoardService.getMonth(year);
            return VietTienResponseDto.ok(month, "Get list account success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
