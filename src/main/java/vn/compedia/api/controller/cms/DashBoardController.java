package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.response.DashBoardResponse;
import vn.compedia.api.service.DashBoardService;

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
}
