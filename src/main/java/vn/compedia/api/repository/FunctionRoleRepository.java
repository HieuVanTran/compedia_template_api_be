package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.compedia.api.entity.FunctionRole;

public interface FunctionRoleRepository extends JpaRepository<FunctionRole, Long>, FunctionRoleRepositoryCustom {
}
