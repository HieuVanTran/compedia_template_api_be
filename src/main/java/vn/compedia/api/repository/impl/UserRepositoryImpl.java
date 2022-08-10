package vn.compedia.api.repository.impl;

import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import vn.compedia.api.repository.BookRepositoryCustom;
import vn.compedia.api.repository.UserRepositoryCustom;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.book.UserResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserResponse> search(String fullName) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.card_id, u.full_name "+
                "FROM user u WHERE 1 = 1");
        appendQuery(sb, fullName);
        Query query = createQuery(sb, fullName);

        List<UserResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
         UserResponse dto = new UserResponse();
            dto.setCardId(ValueUtil.getLongByObject(obj[0]));
            dto.setFullName(ValueUtil.getStringByObject(obj[1]));
            list.add(dto);
        }

        return list;
    }


    public void appendQuery(StringBuilder sb, String fullName) {
        if (StringUtils.isNotBlank(fullName)) {
            sb.append(" and u.full_name like :fullName ");
        }
        sb.append(" order by u.card_id desc ");
    }


    public Query createQuery(StringBuilder sb, String fullName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(fullName)) {
            query.setParameter("fullName", buildFilterLike(fullName));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
