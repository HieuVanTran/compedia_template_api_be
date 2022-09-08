package vn.compedia.api.controller.fe;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.service.HomeRequestService;

@Api(tags = "Fe-HomeRequestController")
@RestController
@RequestMapping("/api/v1/home-request")
@Validated

public class HomeRequestController {

    @Autowired
    private HomeRequestService homeRequestService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CallCardCreateRequest request) {
        try {
            homeRequestService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

}
