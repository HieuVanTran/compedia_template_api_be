package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.NewStaff;

import java.util.List;
@Repository
public interface NewStaffRepository extends JpaRepository<NewStaff, Long>, NewStaffRepositoryCustom {

    @Query("select ns from NewStaff ns where ns.nameStaff like :nameStaff")
    List<NewStaff> findByNameStaff(@Param("nameStaff") String nameStaff);

//    @Query("select ns from NewStaff ns where ns.nameStaff like ?1 and ns.address = ?2")
//    List<NewStaff> findByNameStaff(String nameStaff, String address);

}
