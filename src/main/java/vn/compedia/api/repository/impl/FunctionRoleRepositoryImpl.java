package vn.compedia.api.repository.impl;
import vn.compedia.api.repository.FunctionRoleRepositoryCustom;
import vn.compedia.api.response.FunctionRoleResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FunctionRoleRepositoryImpl implements FunctionRoleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FunctionRoleResponse> findAll(Integer roleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT f.function_role_id," +
                "       r.role_id," +
                "       r.name as role_name," +
                "       r.code_role," +
                "       f.status," +
                "       f.function " +
                "FROM function_role f " +
                "         INNER JOIN role r on f.role_id = r.role_id " +
                "WHERE r.role_id = :roleId ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("roleId", roleId);
        List<Object[]> result = query.getResultList();

        List<FunctionRoleResponse> list = new ArrayList<>();
        for (Object[] obj : result) {
            FunctionRoleResponse dto = new FunctionRoleResponse();
            dto.setFunctionRoleId(ValueUtil.getLongByObject(obj[0]));
            dto.setRoleId(ValueUtil.getIntegerByObject(obj[1]));
            dto.setNameRole(ValueUtil.getStringByObject(obj[2]));
            dto.setCodeRole(ValueUtil.getStringByObject(obj[3]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[4]));
            dto.setFunction(ValueUtil.getStringByObject(obj[5]));
            list.add(dto);
        }
        return list;
    }

}
