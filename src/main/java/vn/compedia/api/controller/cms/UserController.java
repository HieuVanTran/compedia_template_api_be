package vn.compedia.api.controller.cms;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.User;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.user.UserResponse;
import vn.compedia.api.service.UserService;

import java.util.List;

@Api(tags = "User(locked)")
@RestController
@RequestMapping("/api/v1/user")
@Validated

public class UserController extends GlobalExceptionHandler {
    @Autowired
    private UserService userService;

    @GetMapping(value = "user")
    public ResponseEntity<?> getAll() {
        List<UserResponse> list = userService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        User user = userService.getOne(Id);
        return VietTienResponseDto.ok(user, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "fullName", required = false) String fullName,
                                    @RequestParam(name = "address", required = false) String address,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sort_field", required = false) String sortField,
                                    @RequestParam(name = "sort_order", required = false) String sortOrder,
                                    @RequestParam(name = "phone", required = false) String phone) {
        Page<UserResponse> list = userService.search(fullName, address, phone, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateRequest request) {
        try {
            userService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody UserCreateRequest request) {
        try {
            userService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        userService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}