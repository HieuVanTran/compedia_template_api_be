package vn.compedia.api.repository;

import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;
import vn.compedia.api.response.admin.AccountNeResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepositoryCustom {

    Optional<Account> findAccountByEmailAndUserName(String email, String userName);

    List<AccountNeResponse> getAllAccount();

}
