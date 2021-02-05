package vn.compedia.api.repository.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.compedia.api.repository.AbstractExtRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractExtRepositoryImpl<T> implements AbstractExtRepository<T> {

    @Autowired
    private EntityManager entityManager;

    protected abstract StringBuilder sqlBuilder();

    @Override
    public Page<T> search(Map<String, Object> filters, Pageable pageable) {
        StringBuilder sql = sqlBuilder();
        sql.append(buildSql(filters));

        return doSearch(sql.toString(), filters, pageable, null);
    }

    ;

    @Override
    public Page<T> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable) {
        StringBuilder sql = sqlBuilder();
        sql.append(buildSql(filters));

        return doSearch(sql.toString(), filters, pageable, sorts);
    }

    protected StringBuilder buildSql(Map<String, Object> filters) {
        StringBuilder builder = new StringBuilder();

        if (filters.containsKey("from_date")) {
            builder.append(" AND T.createDate >= :from_date");
        }

        if (filters.containsKey("to_date")) {
            builder.append(" AND T.createDate <= :to_date");
        }

        return builder;
    }

    protected void setParams(Query query, Map<String, Object> filters) {
        if (filters.containsKey("from_date")) {
            query.setParameter("from_date", filters.get("from_date"));
        }
        if (filters.containsKey("to_date")) {
            query.setParameter("to_date", filters.get("to_date"));
        }
    }

    protected String buildSort(Map<String, String> sorts) {
        StringBuilder builder = new StringBuilder(" ORDER BY ");
        if (sorts.isEmpty()) {
            builder.append(" T.createDate DESC");
        } else {
            List<String> orders = Lists.newArrayList();
            for (Map.Entry<String, String> k : sorts.entrySet()) {
                orders.add(String.format(" T.%s %s ", k.getKey(), k.getValue()));
            }
            builder.append(String.join(", ", orders));
        }
        return builder.toString();
    }

    private Page<T> doSearch(String sql, Map<String, Object> filters, Pageable pageable, Map<String, String> sorts) {
        Query count = entityManager.createQuery("select count(T) " + sql);

        Query query = CollectionUtils.isEmpty(sorts) ? entityManager.createQuery(sql) : entityManager.createQuery(sql + buildSort(sorts));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        setParams(count, filters);
        setParams(query, filters);

        Object countResult = count.getSingleResult();
        long count_ = countResult == null ? 0 : ((Number) countResult).longValue();

        List<T> results = query.getResultList();

        return new PageImpl<>(results, pageable, count_);
    }
}
