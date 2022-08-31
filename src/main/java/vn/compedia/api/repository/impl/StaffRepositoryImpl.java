package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.StaffRepositoryCustom;
import vn.compedia.api.response.admin.StaffResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StaffRepositoryImpl implements StaffRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StaffResponse> getAllStaff() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.staff_id, s.name_staff, s.phone_number, s.address, s.date_of_birth " +
                "FROM staff s WHERE 1 = 1");
        sb.append(" ORDER BY s.staff_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<StaffResponse> StaffResponse = new ArrayList<>();
        for (Object[] obj : result) {
            StaffResponse dto = new StaffResponse();
            dto.setStaffId(ValueUtil.getLongByObject(obj[0]));
            dto.setNameStaff(ValueUtil.getStringByObject(obj[1]));
            dto.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
            dto.setAddress(ValueUtil.getStringByObject(obj[3]));
            dto.setDateOfBirth(ValueUtil.getStringByObject(obj[4]));
            StaffResponse.add(dto);
        }
        return StaffResponse;
    }

    @Override
    public Page<StaffResponse> search(String nameStaff, String phoneNumber, String address,
                                      String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.staff_id, s.name_staff, s.phone_number, s.address, s.date_of_birth " +
                "FROM staff s WHERE 1 = 1");
        appendQuery(sb, nameStaff, phoneNumber, address);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, nameStaff, phoneNumber, address);
        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }

        List<StaffResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            StaffResponse dto = new StaffResponse();
            dto.setStaffId(ValueUtil.getLongByObject(obj[0]));
            dto.setNameStaff(ValueUtil.getStringByObject(obj[1]));
            dto.setPhoneNumber(ValueUtil.getStringByObject(obj[2]));
            dto.setAddress(ValueUtil.getStringByObject(obj[3]));
            dto.setDateOfBirth(ValueUtil.getStringByObject(obj[4]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(nameStaff, phoneNumber, address).longValue());
    }

    private BigInteger countSearch(String nameStaff, String phoneNumber, String address) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM staff s WHERE 1 = 1 ");
        appendQuery(sb, nameStaff, phoneNumber, address);
        Query query = createQuery(sb, nameStaff, phoneNumber, address);
        return (BigInteger) query.getSingleResult();
    }


    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("nameStaff")) {
                sb.append(" s.nameStaff ");
            }
            if (sortField.toLowerCase().equals("phoneNumber")) {
                sb.append(" s.phoneNumber ");
            } else if (sortField.toLowerCase().equals("address")) {
                sb.append(" s.address ");

                sb.append(sortOrder);
            } else {
                sb.append(" ORDER BY user_id DESC");
            }
        }
    }

    private void appendQuery(StringBuilder sb, String nameStaff, String phoneNumber, String address) {
        if (StringUtils.isNotBlank(nameStaff)) {
            sb.append(" and s.name_staff like :nameStaff");
        }
        if (StringUtils.isNotBlank(phoneNumber)) {
            sb.append(" and s.phone_number like :phoneNumber");
        }
        if (StringUtils.isNotBlank(address)) {
            sb.append(" and s.address like :address");
        }
    }


    public Query createQuery(StringBuilder sb, String nameStaff, String phoneNumber, String address) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(nameStaff)) {
            query.setParameter("nameStaff", buildFilterLike(nameStaff));
        }
        if (StringUtils.isNotBlank(phoneNumber)) {
            query.setParameter("phoneNumber", buildFilterLike(phoneNumber));
        }
        if (StringUtils.isNotBlank(address)) {
            query.setParameter("address", buildFilterLike(address));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
