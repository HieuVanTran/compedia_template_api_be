package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.AdminRepositoryCustom;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<AdminResponse> search(String username, String email, Integer roleId,String codeRole, String fullName,
                                      String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.account_id, a.username, a.email, a.role_id, r.code_role, a.full_name " +
                "FROM account a" +
                "         INNER JOIN role r ON a.role_id = r.role_id " +
                "WHERE 1 = 1  ");

        appendQuery(sb, username, email, roleId,codeRole, fullName);

        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, username, email, roleId,codeRole, fullName);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }

        List<AdminResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            AdminResponse dto = new AdminResponse();
            dto.setAccountId(ValueUtil.getLongByObject(obj[0]));
            dto.setUsername(ValueUtil.getStringByObject(obj[1]));
            dto.setEmail(ValueUtil.getStringByObject(obj[2]));
            dto.setRoleId(ValueUtil.getLongByObject(obj[3]));
            dto.setCodeRole(ValueUtil.getStringByObject(obj[4]));
            dto.setFullName(ValueUtil.getStringByObject(obj[5]));

            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(username, email, roleId, codeRole, fullName).longValue());
    }

    private BigInteger countSearch(String username, String email, Integer roleId, String codeRole, String fullName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM account a WHERE 1 = 1 ");
        appendQuery(sb, username, email, roleId, codeRole, fullName);
        Query query = createQuery(sb, username, email, roleId,codeRole, fullName);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("USERNAME")) {
                sb.append(" a.USERNAME ");
            } else if (sortField.toLowerCase().equals("EMAIL")) {
                sb.append(" a.EMAIL ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY account_id DESC");
        }
    }


    public void appendQuery(StringBuilder sb, String username, String email, Integer roleId, String codeRole, String fullName) {
        if (StringUtils.isNotBlank(username)) {
            sb.append(" and a.username like :username ");
        }
        if (StringUtils.isNotBlank(email)) {
            sb.append(" and a.email like :email ");
        }

        if (roleId != null) {
            sb.append("and a.role_id like :roleId ");
        }
        if (StringUtils.isNotBlank(codeRole)) {
            sb.append("and r.code_role like :codeRole ");
        }
        if (StringUtils.isNotBlank(fullName)) {
            sb.append("and a.full_name like :fullName ");
        }
    }


    public Query createQuery(StringBuilder sb, String username, String email, Integer roleId, String codeRole, String fullName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(username)) {
            query.setParameter("username", buildFilterLike(username));
        }
        if (StringUtils.isNotBlank(email)) {
            query.setParameter("email", buildFilterLike(email));
        }

        if (roleId != null) {
            query.setParameter("roleId", roleId);
        }
        if (StringUtils.isNotBlank(codeRole)) {
            query.setParameter("codeRole", buildFilterLike(codeRole));
        }
        if (StringUtils.isNotBlank(fullName)) {
            query.setParameter("fullName", buildFilterLike(fullName));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}