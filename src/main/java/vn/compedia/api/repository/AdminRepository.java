package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.Author;
import vn.compedia.api.entity.PublishCompany;
import vn.compedia.api.response.AccountResponse;

import java.util.List;

public interface AdminRepository extends JpaRepository<Account, Long>, AdminRepositoryCustom {
    @Query("select a from Account a where a.username like :username")
    List<Account> findByName(@Param("username")String username);

}
