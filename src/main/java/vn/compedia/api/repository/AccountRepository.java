package vn.compedia.api.repository;

import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.controller.auth.AccountController;
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.impl.AccountExtRepositoryImpl;
import vn.compedia.api.response.AccountResponse;

import java.util.List;
@Repository


public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByEmail(String email);

	Account findAccountByAccountId(Long id);

	List<Account> findAccountByPhone(String phone);
}
