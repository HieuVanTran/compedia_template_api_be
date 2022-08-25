package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.CallCardRepositoryCustom;
import vn.compedia.api.response.MonthDataResponse;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CallCardRepositoryImpl implements CallCardRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CallCardResponse> findAllCustomCallCardList() {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ca.call_card_id," +
                "       u.card_number," +
                "       s.staff_id," +
                "       ca.status," +
                "       s.name_staff," +
                "       ccd.call_card_details_id," +
                "       ca.note," +
                "       ca.start_date," +
                "       ca.end_date,  " +
                "       u.user_id " +
                "FROM call_card ca " +
                "         INNER JOIN call_card_details ccd on ca.call_card_id = ccd.call_card_id " +
                "         INNER JOIN staff s on ca.staff_id = s.staff_id " +
                "         INNER JOIN user u on ca.user_id = u.user_id " +
                "WHERE 1 = 1 ");
        sb.append(" ORDER BY ca.call_card_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<CallCardResponse> CallCardResponses = new ArrayList<>();
        for (Object[] obj : result) {
            CallCardResponse dto = new CallCardResponse();
            dto.setCallCardId(ValueUtil.getLongByObject(obj[0]));
            dto.setCardNumber(ValueUtil.getStringByObject(obj[1]));
            dto.setStaffId(ValueUtil.getLongByObject(obj[2]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[3]));
            dto.setNameStaff(ValueUtil.getStringByObject(obj[4]));
            dto.setCallCardDetailsId(ValueUtil.getLongByObject(obj[5]));
            dto.setNote(ValueUtil.getStringByObject(obj[6]));
            dto.setStartDate(ValueUtil.getStringByObject(obj[7]));
            dto.setEndDate(ValueUtil.getStringByObject(obj[8]));
            dto.setUserId(ValueUtil.getLongByObject(obj[9]));

            CallCardResponses.add(dto);
        }
        return CallCardResponses;
    }

    @Override
    public Page<CallCardResponse> search(String cardNumber, Integer status, String nameStaff,
                                         String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ca.call_card_id," +
                "       u.card_number," +
                "       s.staff_id," +
                "       ca.status," +
                "       s.name_staff," +
                "       ccd.call_card_details_id," +
                "       u.user_id " +
                "" +
                "FROM call_card ca " +
                "         INNER JOIN call_card_details ccd on ca.call_card_id = ccd.call_card_id  " +
                "         INNER JOIN staff s on ca.staff_id = s.staff_id " +
                "        INNER JOIN user u on ca.user_id = u.user_id " +
                "" +
                "WHERE 1 = 1");
        appendQuery(sb, cardNumber, status, nameStaff);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, cardNumber, status, nameStaff);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<CallCardResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            CallCardResponse dto = new CallCardResponse();
            dto.setCallCardId(ValueUtil.getLongByObject(obj[0]));
            dto.setCardNumber(ValueUtil.getStringByObject(obj[1]));
            dto.setStaffId(ValueUtil.getLongByObject(obj[2]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[3]));
            dto.setNameStaff(ValueUtil.getStringByObject(obj[4]));
            dto.setCallCardDetailsId(ValueUtil.getLongByObject(obj[5]));
            dto.setUserId(ValueUtil.getLongByObject(obj[6]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(cardNumber, status, nameStaff).longValue());
    }

    private BigInteger countSearch(String cardNumber, Integer status, String nameStaff) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM call_card ca WHERE 1 = 1 ");
        appendQuery(sb, cardNumber, status, nameStaff);
        Query query = createQuery(sb, cardNumber, status, nameStaff);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("cardNumber")) {
                sb.append(" ca.cardNumber ");
            } else if (sortField.toLowerCase().equals("nameStaff")) {
                sb.append(" s.nameStaff ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY call_card_id DESC");
        }
    }

    public void appendQuery(StringBuilder sb, String cardNumber, Integer status, String nameStaff) {
        if (StringUtils.isNotBlank(cardNumber)) {
            sb.append(" and ca.card_number like :cardNumber ");
        }
        if (status != null) {
            sb.append("and sh.status = :status ");
        }
        if (StringUtils.isNotBlank(nameStaff)) {
            sb.append(" and s.name_staff like :nameStaff ");
        }


    }

    public Query createQuery(StringBuilder sb, String cardNumber, Integer status, String nameStaff) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(cardNumber)) {
            query.setParameter("cardNumber", buildFilterLike(cardNumber));
        }
        if (status != null) {
            query.setParameter("status", status);
        }
        if (StringUtils.isNotBlank(nameStaff)) {
            query.setParameter("nameStaff", buildFilterLike(nameStaff));
        }
        return query;
    }

    @Override
    public List<MonthDataResponse> getAmountBorrow() {
        StringBuilder sb = new StringBuilder();
        sb.append("select month_idex as month, sum(brrow) as totalBrrow, sum(pay) as totalPay from (select month_idex, case when totalAmountBorrow is null then 0 else totalAmountBorrow end as brrow, 0 as pay" +
                "               from times" +
                "                        left join (select count(cc.call_card_id) as totalAmountBorrow, MONTH(cc.start_date) as month" +
                "                                   from call_card cc" +
                "                                   where cc.start_date is not null" +
                "                                     and YEAR(cc.start_date) = 2022" +
                "                                   group by month) result on result.month = times.month_idex" +
                "               union all" +
                "               select month_idex, 0 as brrow, case when 0 is null then totalAmountPay else totalAmountPay end as pay" +
                "               from times" +
                "                        left join (select count(cc.call_card_id) as totalAmountPay, MONTH(cc.end_date) as month" +
                "                                   from call_card cc" +
                "                                   where cc.note is not null" +
                "                                     and YEAR(cc.end_date) = 2022" +
                "                                   group by month) result on result.month = times.month_idex) result group by month_idex");

        Query query = entityManager.createNativeQuery(sb.toString());
//        query.setParameter("year", buildFilterLike(year));
        List<MonthDataResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            MonthDataResponse dto = new MonthDataResponse();
            dto.setMonth(ValueUtil.getIntegerByObject(obj[0]));
            dto.setMonthText("Tháng " + obj[0]);
            dto.setAmountBorrow(ValueUtil.getIntegerByObject(obj[1]));
            dto.setAmountPay(ValueUtil.getIntegerByObject(obj[2]));
            list.add(dto);
        }

        return list;
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}