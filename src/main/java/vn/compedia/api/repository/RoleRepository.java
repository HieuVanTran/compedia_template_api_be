package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Page<Role> search(String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);
}
