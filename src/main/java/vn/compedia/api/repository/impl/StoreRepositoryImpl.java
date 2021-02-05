package vn.compedia.api.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.StoreRepositoryCustom;
import vn.compedia.api.response.product.WarehouseResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreRepositoryImpl implements StoreRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${vn.compedia.static.context}")
    private String staticContext;

    @Override
    public Page<WarehouseResponse> searchWarehouse(Map<String, Object> filters, Map<String, String> sorts, Pageable pageable, Integer languageId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sp.product_id, sp.images, sp.name, sp.name_vi, sp.code, sp.price, pt.type_name, st.quantity ");
        appendQueryWarehouse(sb, filters, languageId);
        Query query = createQueryWarehouse(sb, filters, languageId);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<WarehouseResponse> listProduct = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        for (Object[] obj : result) {
            WarehouseResponse dto = new WarehouseResponse();
            dto.setProductId(ValueUtil.getLongByObject(obj[0]));
            dto.setImages(ValueUtil.getStringByObject(obj[1]));
            dto.setName(ValueUtil.getStringByObject(obj[2]));
            dto.setNameVi(ValueUtil.getStringByObject(obj[3]));
            dto.setCode(ValueUtil.getStringByObject(obj[4]));
            dto.setPrice(ValueUtil.getDoubleByObject(obj[5]));
            dto.setTypeName(ValueUtil.getStringByObject(obj[6]));
            dto.setQuantity(ValueUtil.getIntegerByObject(obj[7]));
            listProduct.add(dto);
        }
        return new PageImpl<>(listProduct, pageable, countSearchWarehouse(filters, languageId).longValue());
    }

    public void appendQueryWarehouse(StringBuilder sb, Map<String, Object> filters, Integer languageId) {
        sb.append(" from product sp INNER JOIN  product_type pt on sp.product_id = pt.product_id INNER JOIN  store st on sp.product_id = st.product_id ");
        sb.append(" where 1 = 1 ");
        if (filters.containsKey("name")) {
            sb.append(" AND sp.name LIKE :name ");
        }

        if (filters.containsKey("quantity_start")) {
            sb.append(" AND sp.quantity >= :quantity_start ");
        }
        if (filters.containsKey("quantity_end")) {
            sb.append(" AND sp.quantity <= :quantity_end ");
        }
        sb.append(" AND sp.status = 1 ");
        sb.append(" order by sp.create_date desc ");
    }

    public Query createQueryWarehouse(StringBuilder sb, Map<String, Object> filters, Integer languageId) {
        Query query = entityManager.createNativeQuery(sb.toString());

        if (filters.containsKey("name")) {
            String queryRegex = buildFilterLike(filters.get("name").toString());
            query.setParameter("name", queryRegex);
        }

        if (filters.containsKey("quantity_start")) {
            Long queryRegex = Long.parseLong(filters.get("quantity_start").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("quantity_start", queryRegex);
            }
        }
        if (filters.containsKey("quantity_end")) {
            Long queryRegex = Long.parseLong(filters.get("quantity_end").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("quantity_end", queryRegex);
            }
        }
        return query;
    }

    public BigInteger countSearchWarehouse(Map<String, Object> filters, Integer languageId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(sp.product_id) ");
        appendQueryWarehouse(sb, filters, languageId);
        Query query = createQueryWarehouse(sb, filters, languageId);
        return (BigInteger) query.getSingleResult();

    }


    public BigInteger countSearch(Map<String, Object> filters, Integer languageId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(sp.product_id) ");
        appendQuery(sb, filters, languageId);
        Query query = createQuery(sb, filters, languageId);
        return (BigInteger) query.getSingleResult();

    }

    public void appendQuery(StringBuilder sb, Map<String, Object> filters, Integer languageId) {
        sb.append(" from product sp INNER JOIN  product_category pc on sp.product_category_id = pc.product_category_id INNER JOIN  product_category_lang pcl on pc.product_category_id = pcl.product_category_id " +
                " INNER JOIN  object_group og on sp.object = og.object_group_id " +
                "INNER JOIN object_group_lang ogl on og.object_group_id = ogl.object_group_id " +
                "INNER JOIN  product_lang pl on  pl.product_id = sp.product_id ");
        sb.append(" where 1 = 1  and pcl.language_id = :language_id " +
                " and ogl.language_id = :language_id and pl.language_id = :language_id ");
        if (filters.containsKey("name")) {
            sb.append(" AND sp.name LIKE :name ");
        }
        if (filters.containsKey("description")) {
            sb.append(" AND sp.description = :description ");
        }
        if (filters.containsKey("category_product_id")) {
            sb.append(" AND sp.product_category_id = :category_product_id ");
        }
        if (filters.containsKey("price_start")) {
            sb.append(" AND sp.retail_price >= :price_start ");
        }
        if (filters.containsKey("price_end")) {
            sb.append(" AND sp.retail_price <= :price_end ");
        }
        if (filters.containsKey("quantity_in_stock")) {
            sb.append(" AND sp.quantity_in_stock >= :quantity_in_stock_start ");
        }
        if (filters.containsKey("quantity_in_stock")) {
            sb.append(" AND sp.quantity_in_stock <= :quantity_in_stock_end ");
        }
        sb.append(" AND sp.status = 1 ");
        sb.append(" order by sp.create_date desc ");
    }

    public Query createQuery(StringBuilder sb, Map<String, Object> filters, Integer languageId) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (filters.containsKey("category_product_id")) {
            Long queryRegex = Long.parseLong(filters.get("category_product_id").toString());
            query.setParameter("category_product_id", queryRegex);
        }
        if (filters.containsKey("description")) {
            String queryRegex = buildFilterLike(filters.get("description").toString());
            query.setParameter("description", queryRegex);
        }
        if (filters.containsKey("name")) {
            String queryRegex = buildFilterLike(filters.get("name").toString());
            query.setParameter("name", queryRegex);
        }
        if (filters.containsKey("price_start")) {
            Long queryRegex = Long.parseLong(filters.get("price_start").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("price_start", queryRegex);
            }
        }
        if (filters.containsKey("price_end")) {
            Long queryRegex = Long.parseLong(filters.get("price_end").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("price_end", queryRegex);
            }
        }

        if (filters.containsKey("quantity_in_stock_start")) {
            Long queryRegex = Long.parseLong(filters.get("quantity_in_stock_start").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("quantity_in_stock_start", queryRegex);
            }
        }
        if (filters.containsKey("quantity_in_stock_end")) {
            Long queryRegex = Long.parseLong(filters.get("quantity_in_stock_end").toString());
            if (queryRegex != null && queryRegex > 0) {
                query.setParameter("quantity_in_stock_end", queryRegex);
            }
        }
        query.setParameter("language_id", languageId);
        return query;
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
