package vn.compedia.api.controller.cms;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.NewStaff;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.NewStaffRequest;
import vn.compedia.api.response.NewStaffResponse;
import vn.compedia.api.service.NewStaffService;

import java.util.List;

@Api(tags = "new-staff")
@RequestMapping("/api/vi/newStaff")
@RestController
public class NewStaffController extends GlobalExceptionHandler {
    @Autowired
    private NewStaffService newStaffService;

    @GetMapping(value = "get-all")
    public ResponseEntity<?> getAll() {
        List<NewStaff> list = newStaffService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        NewStaff ns = newStaffService.getOne(Id);
        return VietTienResponseDto.ok(ns, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder) {
        Page<NewStaffResponse> list = newStaffService.search(keyword, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewStaffRequest request) {
        try {
            newStaffService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save Success");
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody NewStaffRequest request) {
        try {
            newStaffService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        newStaffService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }

}
