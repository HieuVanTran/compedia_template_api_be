package vn.compedia.api.controller.auth;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.User;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.book.UserResponse;
import vn.compedia.api.service.UserService;

import java.util.List;

@Api(tags = "User")
@RestController
@RequestMapping("/api/v1/user")
@Validated

public class UserController extends GlobalExceptionHandler {
    @Autowired
    private UserService userService;

    @GetMapping(value = "user")
    public ResponseEntity<?> getAll() {
        List<User> list = userService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        User user = userService.getOne(Id);
        return VietTienResponseDto.ok(user, "Get list account success");
    }
    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "fullName", required = false) String fullName) {
        List<UserResponse> list = userService.search(fullName);
        return VietTienResponseDto.ok(list, "Search list book success");
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateRequest request) {
        userService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody UserCreateRequest request) {
        userService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        userService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}