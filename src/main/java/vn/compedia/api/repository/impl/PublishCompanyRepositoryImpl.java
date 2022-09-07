package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.PublishCompanyRepositoryCustom;
import vn.compedia.api.response.book.PublishCompanyResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class PublishCompanyRepositoryImpl implements PublishCompanyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PublishCompanyResponse> getAllPublishCompany() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.company_id," +
                "       p.publish_name," +
                "       p.email," +
                "       p.agent_people," +
                "       p.address," +
                "       p.date_founding " +
                "" +
                "FROM publish_company p " +
                "WHERE 1 = 1  ");
        sb.append(" ORDER BY p.company_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<PublishCompanyResponse> PublishCompanyResponse = new ArrayList<>();
        for (Object[] obj : result) {
            PublishCompanyResponse dto = new PublishCompanyResponse();
            dto.setIdPub(ValueUtil.getLongByObject(obj[0]));
            dto.setPublishName(ValueUtil.getStringByObject(obj[1]));
            dto.setEmail(ValueUtil.getStringByObject(obj[2]));
            dto.setAgentPeople(ValueUtil.getStringByObject(obj[3]));
            dto.setAddress(ValueUtil.getStringByObject(obj[4]));
            dto.setDateFounding(ValueUtil.getStringByObject(obj[5]));
            PublishCompanyResponse.add(dto);
        }
        return PublishCompanyResponse;
    }


    @Override
    public Page<PublishCompanyResponse> search(Long companyId, String email, String agentPeople,
                                               String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT p.company_id," +
                "       p.publish_name," +
                "       p.email," +
                "       p.agent_people," +
                "       p.address," +
                "       p.date_founding " +
                "" +
                "FROM publish_company p " +
                "WHERE 1 = 1  ");
        appendQuery(sb, companyId, email, agentPeople);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, companyId, email, agentPeople);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<PublishCompanyResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            PublishCompanyResponse dto = new PublishCompanyResponse();
            dto.setIdPub(ValueUtil.getLongByObject(obj[0]));
            dto.setPublishName(ValueUtil.getStringByObject(obj[1]));
            dto.setEmail(ValueUtil.getStringByObject(obj[2]));
            dto.setAgentPeople(ValueUtil.getStringByObject(obj[3]));
            dto.setAddress(ValueUtil.getStringByObject(obj[4]));
            dto.setDateFounding(ValueUtil.getStringByObject(obj[5]));

            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(companyId, email, agentPeople).longValue());
    }

    private BigInteger countSearch(Long companyId, String email, String agentPeople) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM publish_company p WHERE 1 = 1 ");
        appendQuery(sb, companyId, email, agentPeople);
        Query query = createQuery(sb, companyId, email, agentPeople);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("publishName")) {
                sb.append(" p.publishName ");
            } else if (sortField.toLowerCase().equals("email")) {
                sb.append(" p.email ");
            } else if (sortField.toLowerCase().equals("agentPeople")) {
                sb.append(" p.agentPeople ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY company_id DESC");
        }
    }


    public void appendQuery(StringBuilder sb, Long companyId, String email, String agentPeople) {
        if (companyId != null) {
            sb.append(" and p.company_id like :companyId ");
        }
        if (StringUtils.isNotBlank(email)) {
            sb.append(" and p.email like :email ");
        }
        if (StringUtils.isNotBlank(agentPeople)) {
            sb.append(" and p.agent_people like :agentPeople ");

        }
    }


    public Query createQuery(StringBuilder sb, Long companyId, String email, String agentPeople) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (companyId != null) {
            query.setParameter("companyId", companyId);
        }
        if (StringUtils.isNotBlank(email)) {
            query.setParameter("email", buildFilterLike(email));
        }
        if (StringUtils.isNotBlank(agentPeople)) {
            query.setParameter("agentPeople", buildFilterLike(agentPeople));
        }
        return query;
    }

    @Override
    public BigInteger getTotalPublisher() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(p.company_id) as totalPublisher FROM publish_company p where 1 = 1");
        Query query = entityManager.createNativeQuery(sb.toString());
        return (BigInteger) query.getSingleResult();
    }

    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}

