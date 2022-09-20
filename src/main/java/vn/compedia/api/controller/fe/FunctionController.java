package vn.compedia.api.controller.fe;

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
import vn.compedia.api.response.FunctionRoleResponse;
import vn.compedia.api.service.FunctionRoleService;
import java.util.List;

@Api(tags = "FunctionController")
@RequestMapping("/api/v1/function")
@RestController
@Validated

public class FunctionController {

    @Autowired
    FunctionRoleService functionRoleService;

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getByRoleId(@RequestParam Integer roleId) {
        try {
            List<FunctionRoleResponse> functions = functionRoleService.getByRoleId(roleId);
            return VietTienResponseDto.ok(functions, "Get list success");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
