package vn.compedia.api.controller;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.exception.GlobalExceptionHandler;

@Api(tags = "Profile")
@RestController
@RequestMapping("/api/v1/profile")
@Validated
public class ProfileController extends GlobalExceptionHandler {

}
