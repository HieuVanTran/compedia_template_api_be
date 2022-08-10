package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Staff;
import vn.compedia.api.entity.User;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.StaffCreateRequest;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.book.UserResponse;
import vn.compedia.api.service.StaffService;
import vn.compedia.api.service.UserService;

import java.util.List;

@Api(tags = "Staff")
@RestController
@RequestMapping("/api/v1/staff")
@Validated


public class StaffController extends GlobalExceptionHandler {
    @Autowired
    private StaffService staffService;

    @GetMapping(value = "staff")
    public ResponseEntity<?> getAll() {
        List<Staff> list = staffService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        Staff staff = staffService.getOne(Id);
        return VietTienResponseDto.ok(staff, "Get list account success");
    }
//    @GetMapping(value = "search")
//    public ResponseEntity<?> search(@RequestParam(name = "nameStaff", required = false) String nameStaff) {
//        List<UserResponse> list = staffService.search(nameStaff);
//        return VietTienResponseDto.ok(list, "Search list book success");
//    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody StaffCreateRequest request) {
        staffService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody StaffCreateRequest request) {
        staffService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        staffService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}

