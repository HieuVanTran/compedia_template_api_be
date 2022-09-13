package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.User;

import java.util.List;
import java.util.Optional;

@Repository


public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    Account findByEmail(String email);

    Account findAccountByAccountId(Long id);

    List<Account> findAccountByPhone(String phone);

    @Query("select a from Account a")
    List<Account> findAll();

    @Query("select a from Account a where a.accountId = :accountId and a.status = 1")
    Optional<Account> findByUsername(@Param("accountId") Long accountId);

}
