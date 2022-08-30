package vn.compedia.api.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.AccountRepositoryCustom;
import vn.compedia.api.response.AccountResponse;
import vn.compedia.api.response.admin.AccountNeResponse;
import vn.compedia.api.response.book.CallCardListResponse;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public Optional<Account> findAccountByEmailAndUserName(String email, String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM account ac " +
                " where ac.email = :email and ac.username = :username ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("email",email);
        query.setParameter("username",userName);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            Object obj = result.get(0);
            Account account  = new Account();
            return Optional.of(account);
        }
        return Optional.empty();
    }

    @Override
    public List<AccountNeResponse> getAllAccount() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.account_id," +
                "       a.username," +
                "       a.password," +
                "       a.create_date," +
                "       r.role_id," +
                "       a.full_name," +
                "       a.email," +
                "       a.phone," +
                "       a.update_date," +
                "       a.date_of_birth," +
                "       r.code_role " +
                "" +
                "FROM account a " +
                "         INNER JOIN role r on a.role_id = r.role_id " +
                "WHERE 1 = 1 ");
        sb.append(" ORDER BY a.account_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<AccountNeResponse>  AccountNeResponse = new ArrayList<>();
        for (Object[] obj : result) {
            AccountNeResponse dto = new  AccountNeResponse();
            dto.setAccountId(ValueUtil.getLongByObject(obj[0]));
            dto.setUsername(ValueUtil.getStringByObject(obj[1]));
            dto.setPassword(ValueUtil.getStringByObject(obj[2]));
            dto.setCreateDate(ValueUtil.getStringByObject(obj[3]));
            dto.setRoleId(ValueUtil.getIntegerByObject(obj[4]));
            dto.setFullName(ValueUtil.getStringByObject(obj[5]));
            dto.setEmail(ValueUtil.getStringByObject(obj[6]));
            dto.setPhone(ValueUtil.getStringByObject(obj[7]));
            dto.setUpdate_date(ValueUtil.getStringByObject(obj[8]));
            dto.setDateOfBirth(ValueUtil.getStringByObject(obj[9]));
            dto.setCodeRole(ValueUtil.getStringByObject(obj[10]));
            AccountNeResponse.add(dto);
        }
        return AccountNeResponse;
    }
    @Override
    public Optional<AccountNeResponse> findByAccountId(Long accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.account_id," +
                "       a.username," +
                "       a.password," +
                "       a.create_date," +
                "       r.role_id," +
                "       a.full_name," +
                "       a.email," +
                "       a.phone," +
                "       a.update_date," +
                "       a.date_of_birth," +
                "       r.code_role " +
                "" +
                "FROM account a " +
                "         INNER JOIN role r on a.role_id = r.role_id " +
                "WHERE a.account_id = :accountId ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("accountId", accountId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                AccountNeResponse dto = new AccountNeResponse();
                dto.setAccountId(ValueUtil.getLongByObject(obj[0]));
                dto.setUsername(ValueUtil.getStringByObject(obj[1]));
                dto.setPassword(ValueUtil.getStringByObject(obj[2]));
                dto.setCreateDate(ValueUtil.getStringByObject(obj[3]));
                dto.setRoleId(ValueUtil.getIntegerByObject(obj[4]));
                dto.setFullName(ValueUtil.getStringByObject(obj[5]));
                dto.setEmail(ValueUtil.getStringByObject(obj[6]));
                dto.setPhone(ValueUtil.getStringByObject(obj[7]));
                dto.setUpdate_date(ValueUtil.getStringByObject(obj[8]));
                dto.setDateOfBirth(ValueUtil.getStringByObject(obj[9]));
                dto.setCodeRole(ValueUtil.getStringByObject(obj[10]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
