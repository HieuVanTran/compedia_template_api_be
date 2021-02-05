package vn.compedia.api.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vn.compedia.api.mapper.EntityMapper;
import vn.compedia.api.repository.customer.CustomerRepositoryCustom;
import vn.compedia.api.response.ShopManage.ShopListResponse;
import vn.compedia.api.util.DbConstant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${vn.compedia.static.context}")
    private String staticContext;

    @Override
    public Page<ShopListResponse> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ct.customer_id AS customerId," +
                "ct.name AS name," +
                "ct.phone AS phone," +
                "ct.logo AS logo," +
                "ct.status AS status ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List sAgencies = EntityMapper.mapper(query, sb.toString(), ShopListResponse.class);
        return new PageImpl<>(sAgencies, pageable, countSearch(filters).longValue());
    }

    public BigInteger countSearch(Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(ct.customer_id) ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQuery(StringBuilder sb, Map<String, Object> filters) {
        sb.append(" from customer ct where 1 = 1 ");
        sb.append(" AND ct.status <> " + DbConstant.STATUS_DELETED);
        if (filters.containsKey("keyword")) {
            sb.append(" AND ct.name LIKE :keyword or ct.phone LIKE :keyword or ct.email LIKE :keyword ");
        }
        if (filters.containsKey("status")) {
            sb.append("and ct.status = :status ");
        }
    }

    public Query createQuery(StringBuilder sb, Map<String, Object> filters) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (filters.containsKey("keyword")) {
            String queryRegex = buildFilterLike(filters.get("keyword").toString());
            query.setParameter("keyword", queryRegex);
        }
        if (filters.containsKey("status")) {
            String queryRegex = filters.get("status").toString();
            query.setParameter("status", queryRegex);
        }
        return query;
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
