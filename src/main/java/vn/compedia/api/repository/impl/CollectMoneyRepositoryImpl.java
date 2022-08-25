package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.CollectMoneyRepositoryCustom;
import vn.compedia.api.repository.StaffRepositoryCustom;
import vn.compedia.api.response.admin.StaffResponse;
import vn.compedia.api.response.book.CollectMoneyResponse;
import vn.compedia.api.response.book.PublishCompanyResponse;
import vn.compedia.api.response.user.UserResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CollectMoneyRepositoryImpl implements CollectMoneyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<CollectMoneyResponse> findAllCollectMoney() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.collect_money_id," +
                "       u.full_name," +
                "       s.staff_id," +
                "       s.name_staff," +
                "       u.user_id," +
                "       c.fined_amount," +
                "       c.proceeds " +
                "" +
                "FROM collect_money c " +
                "         INNER JOIN staff s ON c.staff_id = s.staff_id " +
                "         INNER JOIN user u ON c.user_id = u.user_id " +
                "WHERE 1 = 1 ");
        sb.append(" ORDER BY c.collect_money_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<CollectMoneyResponse>  CollectMoneyResponse = new ArrayList<>();
        for (Object[] obj : result) {
            CollectMoneyResponse dto = new  CollectMoneyResponse();
            dto.setCollectMoneyId(ValueUtil.getLongByObject(obj[0]));
            dto.setFullName(ValueUtil.getStringByObject(obj[1]));
            dto.setStaffId(ValueUtil.getLongByObject(obj[2]));
            dto.setStaffName(ValueUtil.getStringByObject(obj[3]));
            dto.setUserId(ValueUtil.getLongByObject(obj[4]));
            dto.setFiendAmount(ValueUtil.getIntegerByObject(obj[5]));
            dto.setProceeds(ValueUtil.getIntegerByObject(obj[6]));
            CollectMoneyResponse.add(dto);
        }
        return CollectMoneyResponse;
    }

    @Override
    public Page<CollectMoneyResponse> search(String fullName, String nameStaff, String sortField,String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.collect_money_id, u.full_name, s.staff_id, s.name_staff, u.user_id " +
                "FROM collect_money c " +
                "INNER JOIN staff s ON c.staff_id = s.staff_id " +
                "INNER JOIN user u ON c.user_id = u.user_id " +
                "WHERE 1 = 1");

        appendQuery(sb, fullName, nameStaff );
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, fullName,nameStaff );

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<CollectMoneyResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            CollectMoneyResponse dto = new CollectMoneyResponse();
            dto.setUserId(ValueUtil.getLongByObject(obj[0]));
            dto.setFullName(ValueUtil.getStringByObject(obj[1]));
            dto.setStaffId(ValueUtil.getLongByObject(obj[2]));
            dto.setStaffName(ValueUtil.getStringByObject(obj[3]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch( fullName,nameStaff).longValue());
    }

    private BigInteger countSearch(String fullName, String nameStaff) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM collect_money c WHERE 1 = 1 ");
        appendQuery(sb, fullName,nameStaff);
        Query query = createQuery(sb, fullName,nameStaff);
        return (BigInteger) query.getSingleResult();
    }
    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("fullName")) {
                sb.append(" u.fullName ");
            } else if (sortField.toLowerCase().equals("nameStaff")) {
                sb.append(" s.nameStaff ");
            }

            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY collect_money_id DESC");
        }
    }



    public void appendQuery(StringBuilder sb, String fullName, String nameStaff) {
        if (StringUtils.isNotBlank(fullName)) {
            sb.append(" and u.full_name like :fullName ");
        }
        if(StringUtils.isNotBlank(nameStaff)){
            sb.append(" and s.nameStaff like :nameStaff ");
        }

    }

    public Query createQuery(StringBuilder sb, String fullName, String nameStaff ) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(fullName)) {
            query.setParameter("fullName", buildFilterLike(fullName));
        }
        if (StringUtils.isNotBlank(nameStaff)) {
            query.setParameter("nameStaff", buildFilterLike(nameStaff));
        }

        return query;
    }
    @Override
    public BigDecimal getTotalMoney() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(cc.fined_amount) as totalMoney FROM collect_money cc where 1 = 1");
        Query query = entityManager.createNativeQuery(sb.toString());
        return (BigDecimal) query.getSingleResult();
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
