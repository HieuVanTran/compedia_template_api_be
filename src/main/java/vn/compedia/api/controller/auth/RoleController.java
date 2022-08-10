package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Book;
import vn.compedia.api.entity.Role;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.RoleCreateRequest;
import vn.compedia.api.service.RoleService;

import java.util.List;

@Api(tags = "Role")
@RestController
@RequestMapping("/api/v1/role")
@Validated

public class RoleController extends GlobalExceptionHandler {
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "role")
    public ResponseEntity<?> getAll() {
        List<Role> list = roleService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long roleId) {
       Role role = roleService.getOne(roleId);
        return VietTienResponseDto.ok(role, "Get list account success");
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleCreateRequest request) {
        roleService.create(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody RoleCreateRequest request) {
        roleService.update(request);
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id){
        roleService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}
