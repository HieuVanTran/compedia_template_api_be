package vn.compedia.api.repository.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.OrdersDto;
import vn.compedia.api.mapper.EntityMapper;
import vn.compedia.api.repository.OrdersRepositoryCustom;
import vn.compedia.api.util.DateUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrdersRepositoryImpl implements OrdersRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Page<OrdersDto> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select " +
                " o.order_id as orderId," +
                " o.total_money as totalMoney," +
                " o.status as status," +
                " s.name as customerName," +
                " c.name as shopName," +
                " o.day_shipping as dayShipping ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List sAgencies = EntityMapper.mapper(query, sb.toString(), OrdersDto.class);
        return new PageImpl<>(sAgencies, pageable, countSearch(filters).longValue());
    }

    public BigInteger countSearch(Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(o.order_id) ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQuery(StringBuilder sb, Map<String, Object> filters) {
        sb.append(" from orders o" +
                " inner join customer s on s.customer_id = o.customer_id " +
                " inner join customer c on c.customer_id = o.customer_id " +
                " where 1 = 1");
        if (filters.containsKey("from_date")) {
            sb.append(" AND o.from_date >= :from_date ");
        }
        if (filters.containsKey("to_date")) {
            sb.append("and o.to_date <= :to_date ");
        }
    }

    public Query createQuery(StringBuilder sb, Map<String, Object> filters) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (filters.containsKey("from_date")) {
            Date queryRegex = DateUtil.formatDatePattern(filters.get("from_date").toString(), DateUtil.FROM_DATE_FORMAT);
            query.setParameter("from_date", queryRegex);
        }
        if (filters.containsKey("to_date")) {
            Date queryRegex = DateUtil.formatDatePattern(filters.get("to_date").toString(), DateUtil.FROM_DATE_FORMAT);
            query.setParameter("to_date", queryRegex);
        }
        return query;
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }

}
