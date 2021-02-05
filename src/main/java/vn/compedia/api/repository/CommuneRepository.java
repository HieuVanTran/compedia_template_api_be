package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.compedia.api.entity.Commune;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findCommuneByDistrictId(Long districtId);

    @Query("select c.name from Commune c where c.communeId = :communeId")
    String findNameById(Long communeId);
}
