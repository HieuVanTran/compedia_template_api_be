package vn.compedia.api.repository.auth;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;

import java.util.Optional;

@Repository
public interface ApiUserRepository extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {
	Optional<Account> findByEmailAndRoleId(String email, Integer roleId);
}
