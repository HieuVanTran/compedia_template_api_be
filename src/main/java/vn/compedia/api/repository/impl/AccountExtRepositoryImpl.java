package vn.compedia.api.repository.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.AccountExtRepository;

import javax.persistence.Query;
import java.util.Map;

@Repository
@Transactional
@Log4j2
@Component
public class AccountExtRepositoryImpl extends AbstractExtRepositoryImpl<Account> implements AccountExtRepository {

    @Override
    protected StringBuilder sqlBuilder() {
        return new StringBuilder("from Account T where 1 = 1");
    }

    @Override
    protected StringBuilder buildSql(Map<String, Object> filters) {
        StringBuilder builder = super.buildSql(filters);

        if (filters.containsKey("query")) {
            builder.append(" AND (T.fullName LIKE :query ");
            builder.append(" OR T.phone LIKE :query ");
            builder.append(" OR T.email LIKE :query) ");
        }

        return builder;
    }

    @Override
    protected void setParams(Query query, Map<String, Object> filters) {
        super.setParams(query, filters);
        if (filters.containsKey("query")) {
            String queryRegex = buildFilterLike(filters.get("query").toString());
            query.setParameter("query", queryRegex);
        }
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
