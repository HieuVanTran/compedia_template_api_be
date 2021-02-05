package vn.compedia.api.service.common;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.common.CityDistrictDto;
import vn.compedia.api.entity.Commune;
import vn.compedia.api.entity.District;
import vn.compedia.api.entity.Province;
import vn.compedia.api.repository.CommuneRepository;
import vn.compedia.api.repository.DistrictRepository;
import vn.compedia.api.repository.ProvinceRepository;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Getter
@Setter
public class CityDistrictService {

    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private CommuneRepository communeRepository;

    private List<Province> listProvince;
    private List<District> listDistrict;
    private List<Commune> listCommune;
    private boolean checkDisable;

    private CityDistrictDto cityDistrictDto;

    public List<Province> getAllProvince() {
        return provinceRepository.findAll();
    }

    public List<District> findDistrictsByProvinceId(Long provinceId) {
        return districtRepository.findDistrictsByProvinceId(provinceId);
    }

    public List<Commune> findCommuneByDistrictId(Long districtId) {
        return communeRepository.findCommuneByDistrictId(districtId);
    }

    public void resetAll() {
        checkDisable = false;
        cityDistrictDto = new CityDistrictDto();
        listProvince = provinceRepository.findAll();
        onChangeCity();
    }

    public void resetAllValue() {
        cityDistrictDto = new CityDistrictDto();
        listDistrict = new ArrayList<>();
        resetCommune();
    }

    public void resetCommune() {
        listCommune = new ArrayList<>();
    }

    public void onChangeCity() {
        if (cityDistrictDto.getProvinceId() != null) {
            listDistrict = districtRepository.findDistrictsByProvinceId(cityDistrictDto.getProvinceId());
            listCommune = new ArrayList<>();
            cityDistrictDto = new CityDistrictDto();
        } else {
            resetAllValue();
        }
        onChangeDistrict();
    }

    public void onChangeDistrict() {
        if (cityDistrictDto.getDistrictId() != null) {
            listCommune = communeRepository.findCommuneByDistrictId(cityDistrictDto.getDistrictId());
        } else {
            resetCommune();
        }
    }

    public void loadData() {
        if (cityDistrictDto.getProvinceId() != null) {
            listDistrict = districtRepository.findDistrictsByProvinceId(cityDistrictDto.getProvinceId());
        }
        if (cityDistrictDto.getDistrictId() != null) {
            listCommune = communeRepository.findCommuneByDistrictId(cityDistrictDto.getDistrictId());
        }
    }

    public String upperCaseFirstChar(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
