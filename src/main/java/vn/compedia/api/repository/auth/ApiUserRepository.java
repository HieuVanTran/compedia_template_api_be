package vn.compedia.api.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;

import java.util.Optional;

@Repository
public interface ApiUserRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.email = ?1 and u.roleId = ?2")
    Optional<Account> findByEmailAndRoleId(String email, Integer roleId);

    @Query("SELECT u FROM Account u WHERE u.email = ?1 ")
    Optional<Account> findByEmail(String email);

}
