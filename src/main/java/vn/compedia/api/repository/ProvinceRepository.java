package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    @Query("select p.name from Province p where p.provinceId = :provinceId")
    String finNameById(Long provinceId);
}
