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
import vn.compedia.api.entity.Staff;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.StaffCreateRequest;
import vn.compedia.api.response.admin.StaffResponse;
import vn.compedia.api.service.StaffService;

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
        List<StaffResponse> list = staffService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        Staff staff = staffService.getOne(Id);
        return VietTienResponseDto.ok(staff, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "nameStaff", required = false) String nameStaff,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sort_field", required = false) String sortField,
                                    @RequestParam(name = "sort_order", required = false) String sortOrder,
                                    @RequestParam(name = "phone_number", required = false) String phoneNumber,
                                    @RequestParam(name = "address", required = false) String address) {
        Page<StaffResponse> list = staffService.search(nameStaff, phoneNumber, address, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StaffCreateRequest request) {
        try {
            staffService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody StaffCreateRequest request) {
        try {
            staffService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        staffService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}

