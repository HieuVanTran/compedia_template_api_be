package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.mapper.EntityMapper;
import vn.compedia.api.repository.NewStaffRepositoryCustom;
import vn.compedia.api.response.NewStaffResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NewStaffRepositoryImpl implements NewStaffRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<NewStaffResponse> search(String keyword, String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select new_staff_id  as id, " +
                "       code_staff    as codeStaff, " +
                "       name_staff    as nameStaff, " +
                "       date_of_birth as dateStaff, " +
                "       sex           as sexStaff, " +
                "       email         as email, " +
                "       phone         as phone, " +
                "       address       as address, " +
                "       position      as position, " +
                "       department    as department, " +
                "       status        as status, " +
                "       create_date   as dateCreate, " +
                "       create_by     as creator ");
        appendQuery(sb, keyword);
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" order by ");

            if (sortField.equals("code_staff")) {
                sb.append(" code_staff ");
            }

            if (sortField.equals("name_staff")) {
                sb.append(" name_staff ");
            }

            if (sortField.equals("date_of_birth")) {
                sb.append(" date_of_birth ");
            }

            sb.append(sortOrder);
        } else {
            sb.append(" order by new_staff_id desc ");
        }

        Query query = createQuery(sb, keyword);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }

        List result = EntityMapper.mapper(query, sb.toString(), NewStaffResponse.class);
        return new PageImpl<>(result, pageable, count(keyword).longValue());

        // C2 innerJoin
//        NewStaffResponse newStaffResponse;
//        List<NewStaffResponse> responses = new ArrayList<>();

//        List<Object[]> result = query.getResultList();
//        for (Object[] obj : result) {
//            newStaffResponse = new NewStaffResponse();
//            newStaffResponse.setId(ValueUtil.getLongByObject(obj[0]));
//            newStaffResponse.setCodeStaff(ValueUtil.getStringByObject(obj[1]));
//            newStaffResponse.setNameStaff(ValueUtil.getStringByObject(obj[2]));
//            responses.add(newStaffResponse);
//        }
//
//        return new PageImpl<>(responses, pageable, count(keyword).longValue());
    }

    public BigInteger count(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(*) ");
        appendQuery(sb, keyword);
        Query query = createQuery(sb, keyword);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQuery(StringBuilder sb, String keyword) {
        sb.append(" from new_staff where 1 = 1 ");

        if (StringUtils.isNotBlank(keyword)) {
            sb.append(" and code_staff like :keyword or name_staff like :keyword ");
        }
    }

    public Query createQuery(StringBuilder sb, String keyword) {
        Query query = entityManager.createNativeQuery(sb.toString());

        if (StringUtils.isNotBlank(keyword)) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        return query;
    }

}
