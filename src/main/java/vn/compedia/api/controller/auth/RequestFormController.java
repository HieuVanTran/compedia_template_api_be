package vn.compedia.api.controller.auth;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.entity.Author;
import vn.compedia.api.entity.RequestForm;
import vn.compedia.api.exception.GlobalExceptionHandler;
import vn.compedia.api.request.AuthorCreateRequest;
import vn.compedia.api.service.AuthorService;
import vn.compedia.api.service.RequestFormService;

import java.util.List;

@Api(tags = "RequestFrom")
@RestController
@RequestMapping("/api/v1/request-form")
@Validated

public class RequestFormController extends GlobalExceptionHandler {
    @Autowired
    private RequestFormService requestFormService;


    @GetMapping(value = "request-form")
    public ResponseEntity<?> getAll() {
        List<RequestForm> list = requestFormService.getAll();
        return VietTienResponseDto.ok(list, "Get list account success");
    }
    @GetMapping(value = "get-one")
    public ResponseEntity<?> getOne(@RequestParam(name = "id") Long idAuthor) {
        RequestForm requestForm = requestFormService.getOne(idAuthor);
        return VietTienResponseDto.ok(requestForm, "Get list account success");
    }
    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestParam Long id ) {
        requestFormService.delete(id);
        return VietTienResponseDto.ok("", "Save success");
    }
}
