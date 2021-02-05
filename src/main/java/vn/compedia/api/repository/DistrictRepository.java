package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.compedia.api.entity.District;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findDistrictsByProvinceId(Long provinceId);

    @Query("select d.name from District d where d.districtId = :districtId")
    String findNameById(Long districtId);
}
