package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
}
