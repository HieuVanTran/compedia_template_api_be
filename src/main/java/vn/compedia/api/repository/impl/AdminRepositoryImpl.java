package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.api.repository.AdminRepositoryCustom;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AdminResponse> search(String username, String email, String roleID, String fullName) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.account_id,a.username,a.email,a.role_id,a.full_name " +
                "FROM account a WHERE 1 = 1 ");

        appendQuery(sb, username, email, roleID, fullName);
        Query query = createQuery(sb, username, email, roleID, fullName);

        List<AdminResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            AdminResponse dto = new AdminResponse();
            dto.setAccountId(ValueUtil.getLongByObject(obj[0]));
            dto.setUsername(ValueUtil.getStringByObject(obj[1]));
            dto.setEmail(ValueUtil.getStringByObject(obj[2]));
            dto.setRoleId(ValueUtil.getLongByObject(obj[3]));
            dto.setFullName(ValueUtil.getStringByObject(obj[4]));



            list.add(dto);
        }

        return list;
    }


    public void appendQuery(StringBuilder sb, String username, String email, String roleId, String fullName) {
        if (StringUtils.isNotBlank(username)) {
            sb.append(" and a.username like :username ");
        }
        if (StringUtils.isNotBlank(email)) {
            sb.append(" and a.email like :email ");
        }
        if (StringUtils.isNotBlank(fullName)){
            sb.append("and a.full_name like :fullName");
        }
        if (StringUtils.isNotBlank(roleId)){
            sb.append("and a.role_id like :roleId");
        }
        sb.append(" order by a.account_id desc ");
    }


    public Query createQuery(StringBuilder sb, String username, String email, String roleId, String fullName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(username)) {
            query.setParameter("username", buildFilterLike(username));
        }
        if (StringUtils.isNotBlank(email)) {
            query.setParameter("email", buildFilterLike(email));
        }
        if (StringUtils.isNotBlank(fullName)) {
            query.setParameter("fullName", buildFilterLike(fullName));
        }
        if (StringUtils.isNotBlank(roleId)) {
            query.setParameter("roleId", buildFilterLike(roleId));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}