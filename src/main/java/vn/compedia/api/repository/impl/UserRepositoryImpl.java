package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.UserRepositoryCustom;

import vn.compedia.api.response.admin.AccountNeResponse;
import vn.compedia.api.response.user.UserResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserResponse> getAllUser() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.user_id," +
                "       u.full_name," +
                "       u.address," +
                "       u.phone ," +
                "       u.create_date," +
                "       u.expiration_date," +
                "       a.account_id," +
                "       a.email," +
                "       u.card_number " +
                "" +
                "FROM user u " +
                "INNER JOIN account a on u.account_id = a.account_id " +

                "WHERE 1 = 1");
        sb.append(" ORDER BY u.user_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<UserResponse>  UserResponse = new ArrayList<>();
        for (Object[] obj : result) {
            UserResponse dto = new  UserResponse();
            dto.setUserId(ValueUtil.getLongByObject(obj[0]));
            dto.setFullName(ValueUtil.getStringByObject(obj[1]));
            dto.setAddress(ValueUtil.getStringByObject(obj[2]));
            dto.setPhone(ValueUtil.getStringByObject(obj[3]));
            dto.setCreateDate(ValueUtil.getStringByObject(obj[4]));
            dto.setExpirationDate(ValueUtil.getStringByObject(obj[5]));
            dto.setAccountId(ValueUtil.getLongByObject(obj[6]));
            dto.setEmail(ValueUtil.getStringByObject(obj[7]));
            dto.setCardNumber(ValueUtil.getStringByObject(obj[8]));
            UserResponse.add(dto);
        }
        return UserResponse;
    }


    @Override
    public Page<UserResponse> search(String fullName, String address, String phone,
                                     String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.user_id, u.full_name, u.address, u.phone " +
                "FROM user u WHERE 1 = 1");
        appendQuery(sb, fullName, address, phone);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, fullName, address, phone);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<UserResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
         UserResponse dto = new UserResponse();
            dto.setUserId(ValueUtil.getLongByObject(obj[0]));
            dto.setFullName(ValueUtil.getStringByObject(obj[1]));
            dto.setAddress(ValueUtil.getStringByObject(obj[2]));
            dto.setPhone(ValueUtil.getStringByObject(obj[3]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch( fullName,address,phone).longValue());
    }

    private BigInteger countSearch(String fullName, String address, String phone) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM user u WHERE 1 = 1 ");
        appendQuery(sb, fullName,address,phone);
        Query query = createQuery(sb, fullName,address,phone);
        return (BigInteger) query.getSingleResult();
    }
    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("FULLNAME")) {
                sb.append(" u.FULLNAME ");
            } else if (sortField.toLowerCase().equals("ADDRESS")) {
                sb.append(" u.ADDRESS ");
            }

            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY user_id DESC");
        }
    }



    public void appendQuery(StringBuilder sb, String fullName, String address, String phone) {
        if (StringUtils.isNotBlank(fullName)) {
            sb.append(" and u.full_name like :fullName ");
        }
        if(StringUtils.isNotBlank(address)){
            sb.append(" and u.address like :address ");
        }
        if(StringUtils.isNotBlank(phone)) {
            sb.append(" and u.phone like :phone ");
        }
    }


    public Query createQuery(StringBuilder sb, String fullName, String address, String phone) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(fullName)) {
            query.setParameter("fullName", buildFilterLike(fullName));
        }
        if (StringUtils.isNotBlank(address)) {
            query.setParameter("address", buildFilterLike(address));
        }
        if (StringUtils.isNotBlank(phone)) {
            query.setParameter("phone", buildFilterLike(phone));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
