package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Book;
import vn.compedia.api.entity.Role;

@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {
}
