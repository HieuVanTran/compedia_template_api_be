package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.NewStaffRepositoryCustom;
import vn.compedia.api.response.PositionsDepartmentResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NewStaffRepositoryImpl implements NewStaffRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<PositionsDepartmentResponse> search(String keyword, String positionsName, String departmentName, String sortField, String sortOrder, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
//        sb.append(" select new_staff_id  as id, " +
//                "       code_staff    as codeStaff, " +
//                "       name_staff    as nameStaff, " +
//                "       date_of_birth as dateStaff, " +
//                "       sex           as sexStaff, " +
//                "       email         as email, " +
//                "       phone         as phone, " +
//                "       address       as address, " +
//                "       position_id      as position_id, " +
//                "       department_id    as department_id, " +
//                "       status        as status, " +
//                "       create_date   as dateCreate, " +
//                "       create_by     as creator ");


        sb.append(" select new_staff.new_staff_id, " +
                "       code_staff as codeStaff, " +
                "       name_staff as nameStaff, " +
                "       positions.positions_id, " +
                "       positions.positions_name, " +
                "       department.department_id, " +
                "       department.department_name " +
                "FROM new_staff " +
                "         INNER JOIN positions ON new_staff.position_id = positions.positions_id " +
                "         INNER JOIN department ON new_staff.department_id = department.department_id ");

        appendQuery(sb, keyword, positionsName, departmentName);

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

            if (sortField.equals("positions_name")) {
                sb.append(" positions.name ");
            }

            if (sortField.equals("department_name")) {
                sb.append(" department.name ");
            }

            sb.append(sortOrder);
        } else {
            sb.append(" order by new_staff_id desc ");
        }

        Query query = createQuery(sb, keyword, positionsName, departmentName);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }

//        List result = EntityMapper.mapper(query, sb.toString(), NewStaffResponse.class);
//        return new PageImpl<>(result, pageable, count(keyword).longValue());

        // C2 innerJoin
        List<PositionsDepartmentResponse> responses = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();
        for (Object[] obj : resultList) {
            PositionsDepartmentResponse dto = new PositionsDepartmentResponse();
            dto.setId(ValueUtil.getLongByObject(obj[0]));
            dto.setCodeStaff(ValueUtil.getStringByObject(obj[1]));
            dto.setNameStaff(ValueUtil.getStringByObject(obj[2]));
            dto.setPositionsId(ValueUtil.getLongByObject(obj[3]));
            dto.setPositionsName(ValueUtil.getStringByObject(obj[4]));
            dto.setDepartmentId(ValueUtil.getLongByObject(obj[5]));
            dto.setDepartmentName(ValueUtil.getStringByObject(obj[6]));
            responses.add(dto);
        }

        return new PageImpl<>(responses, pageable, count(keyword, positionsName, departmentName).longValue());
    }

    public BigInteger count(String keyword, String positionsName, String departmentName) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(*) "
                + " FROM new_staff "
                + "     INNER JOIN positions ON new_staff.position_id = positions.positions_id "
                + "     INNER JOIN department ON new_staff.department_id = department.department_id ");
        appendQuery(sb, keyword, positionsName, departmentName);
        Query query = createQuery(sb, keyword, positionsName, departmentName);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQuery(StringBuilder sb, String keyword, String positionsName, String departmentName) {
        sb.append(" where 1 = 1 ");

        if (StringUtils.isNotBlank(keyword)) {
            sb.append(" and code_staff like :keyword or name_staff like :keyword ");
        }

        if (StringUtils.isNotBlank(positionsName)) {
            sb.append(" and positions_name like :positionsName ");
        }

        if (StringUtils.isNotBlank(departmentName)) {
            sb.append(" and department_name like :departmentName ");
        }
    }

    public Query createQuery(StringBuilder sb, String keyword, String positionsName, String departmentName) {
        Query query = entityManager.createNativeQuery(sb.toString());

        if (StringUtils.isNotBlank(keyword)) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        if (StringUtils.isNotBlank(positionsName)) {
            query.setParameter("positionsName", "%" + positionsName + "%");
        }

        if (StringUtils.isNotBlank(departmentName)) {
            query.setParameter("departmentName", "%" + departmentName + "%");
        }

        return query;
    }

}
