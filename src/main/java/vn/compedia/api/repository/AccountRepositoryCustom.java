package vn.compedia.api.repository;

import vn.compedia.api.entity.Account;
import vn.compedia.api.response.admin.AccountNeResponse;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<Account> findAccountByEmailAndUserName(String email);

    List<AccountNeResponse> getAllAccount();

    Optional<AccountNeResponse> findByAccountId();


}
