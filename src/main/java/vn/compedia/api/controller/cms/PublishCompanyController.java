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
import vn.compedia.api.entity.PublishCompany;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.PublishCompanyCreateRequest;
import vn.compedia.api.response.book.PublishCompanyResponse;
import vn.compedia.api.service.PublishCompanyService;

import java.util.List;


@Api(tags = "PublishCompany")
@RestController
@RequestMapping("/api/v1/publish-company")
@Validated
public class PublishCompanyController extends GlobalExceptionHandler {

    @Autowired
    private PublishCompanyService publishCompanyService;

    @GetMapping(value = "publish-company")
    public ResponseEntity<?> getAll() {
        List<PublishCompanyResponse> list = publishCompanyService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }

    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long Id) {
        PublishCompany publishCompany = publishCompanyService.getOne(Id);
        return VietTienResponseDto.ok(publishCompany, "Get list account success");
    }

    @GetMapping(value = "search")
    public ResponseEntity<?> search(@RequestParam(name = "publishName", required = false) String publishName,
                                    @RequestParam(name = "email", required = false) String email,
                                    @RequestParam(name = "page") Integer page,
                                    @RequestParam(name = "size") Integer size,
                                    @RequestParam(name = "sortField", required = false) String sortField,
                                    @RequestParam(name = "sortOrder", required = false) String sortOrder,
                                    @RequestParam(name = "agentPeople", required = false) String agentPeople) {
        Page<PublishCompanyResponse> list = publishCompanyService.search(publishName, email, agentPeople, sortField, sortOrder, page, size);
        return VietTienResponseDto.ok(VietTienPageDto.build(list), "Search list book success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PublishCompanyCreateRequest request) {
        try {
            publishCompanyService.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }


    @PutMapping()
    public ResponseEntity<?> update(@RequestBody PublishCompanyCreateRequest request) {
        try {
            publishCompanyService.update(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return VietTienResponseDto.ok("", "Save success");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id) {
        publishCompanyService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}