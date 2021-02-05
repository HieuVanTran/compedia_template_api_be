package vn.compedia.api.controller.common;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.compedia.api.dto.VietTienResponseDto;
import vn.compedia.api.service.common.CityDistrictService;
import vn.compedia.api.util.MessageUtil;

import java.io.Serializable;

@Api(tags = "Common")
@RestController
@RequestMapping("/api/v1/common")
@Validated
public class CityDistrictController implements Serializable {

    @Autowired
    private CityDistrictService cityDistrictService;

    @GetMapping("/province")
    @ApiOperation("Get list all province")
    public ResponseEntity<?> getAllProvince() {
        return VietTienResponseDto.ok(cityDistrictService.getAllProvince(), MessageUtil.GET_ALL_PROVINCE);
    }

    @GetMapping("/district/{id}")
    @ApiOperation("Get list district by province id")
    public ResponseEntity<?> getDistrictsByProvinceId(@PathVariable(value = "id") Long provinceId) {
        return VietTienResponseDto.ok(cityDistrictService.findDistrictsByProvinceId(provinceId), MessageUtil.GET_DISTRICT_BY_PROVINCE);
    }

    @GetMapping("/commune/{id}")
    @ApiOperation("Get list commune by district id")
    public ResponseEntity<?> getCommuneByDistrictId(@PathVariable(value = "id") Long districtId) {
        return VietTienResponseDto.ok(cityDistrictService.findCommuneByDistrictId(districtId), MessageUtil.GET_COMMUNE_BY_DISTRICT);
    }

}
