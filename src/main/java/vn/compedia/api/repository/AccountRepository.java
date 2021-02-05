package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByEmail(String email);

	Account findAccountByAccountId(Long id);

	List<Account> findAccountByPhone(String phone);
}
