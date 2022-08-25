package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Staff;
import vn.compedia.api.entity.User;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> ,StaffRepositoryCustom {
    @Query("select s from Staff s where s.nameStaff like :nameStaff")
    List<Staff> findByName(@Param("nameStaff")String nameStaff);

}