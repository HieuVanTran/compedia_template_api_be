package vn.compedia.api.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.mapper.EntityMapper;
import vn.compedia.api.repository.ShopRepositoryCustom;
import vn.compedia.api.response.shop.ShopResponse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ShopRepositoryImpl implements ShopRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${vn.compedia.static.context}")
    private String staticContext;

    @Override
    public Page<ShopResponse> search(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select sh.shop_id as shopId," +
                "sh.name as shopName," +
                "sh.phone as shopPhone," +
                "sh.email as shopEmail," +
                "sh.facebook as shopFacebook," +
                "sh.logo as shopLogo," +
                "sh.description as shopDescription," +
                "sh.province_id as shopProvinceId," +
                //"pro.name as shopProvinceName," +
                "sh.district_id as shopDistrictId," +
                //"dis.name as shopDistrictName," +
                "sh.commune_id as shopCommuneId," +
                // "com.name as shopCommuneName," +
                "sh.address as shopAddress," +

                "acc.full_name as accountFullName," +
                "acc.phone as accountPhone," +
                "acc.dob as accountDob," +
                "acc.gender as accountGender," +
                "acc.email as accountEmail," +
                "acc.province_id as accountProvinceId," +
                //"acc.name as accountProvinceName," +
                "acc.district_id as accountDistrictId," +
                //  "acc.name as accountDistrictName," +
                "acc.commune_id as accountCommuneId," +
                //    "com.name as accountCommuneName," +
                "acc.images as accountImages ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List sAgencies = EntityMapper.mapper(query, sb.toString(), ShopResponse.class);
        return new PageImpl<>(sAgencies, pageable, countSearch(filters).longValue());
    }

    public BigInteger countSearch(Map<String, Object> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(sh.shop_id) ");
        appendQuery(sb, filters);
        Query query = createQuery(sb, filters);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQuery(StringBuilder sb, Map<String, Object> filters) {
        sb.append(" from shop sh, account acc where sh.account_id = acc.account_id and sh.status = 1");
        if (filters.containsKey("keyword")) {
            sb.append(" AND sh.name LIKE :keyword or sh.phone LIKE :keyword or sh.email LIKE :keyword ");
        }
        if (filters.containsKey("status")) {
            sb.append("and sh.status = :status ");
        }
    }

    public Query createQuery(StringBuilder sb, Map<String, Object> filters) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (filters.containsKey("keyword")) {
            String queryRegex = buildFilterLike(filters.get("keyword").toString());
            query.setParameter("keyword", queryRegex);
        }
        if (filters.containsKey("status")) {
            String queryRegex = buildFilterLike(filters.get("status").toString());
            query.setParameter("status", queryRegex);
        }
        return query;
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
