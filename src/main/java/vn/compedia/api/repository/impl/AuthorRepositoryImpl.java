package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.AuthorRepositoryCustom;
import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.response.index.HomeAuthorResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorResponse> getAllAuthor() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT au.id_author," +
                "       au.name_author," +
                "       au.address," +
                "       au.title," +
                "       au.note " +
                "FROM author au " +
                "WHERE 1 = 1  ");
        sb.append(" ORDER BY au.id_author DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<AuthorResponse> AuthorResponse = new ArrayList<>();
        for (Object[] obj : result) {
            AuthorResponse dto = new AuthorResponse();
            dto.setAuthorId(ValueUtil.getLongByObject(obj[0]));
            dto.setAuthorName(ValueUtil.getStringByObject(obj[1]));
            dto.setAddress(ValueUtil.getStringByObject(obj[2]));
            dto.setTitle(ValueUtil.getStringByObject(obj[3]));
            dto.setNote(ValueUtil.getStringByObject(obj[4]));
            AuthorResponse.add(dto);
        }
        return AuthorResponse;
    }


    @Override
    public Page<AuthorResponse> search(String authorName, String address, String title, String sortField, String sortOrder,
                                       Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT au.id_author, au.name_author, au.address, au.title, au.note " +
                "FROM author au WHERE 1 = 1 ");

        appendQuery(sb, authorName, address, title);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, authorName, address, title);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<AuthorResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            AuthorResponse dto = new AuthorResponse();
            dto.setAuthorId(ValueUtil.getLongByObject(obj[0]));
            dto.setAuthorName(ValueUtil.getStringByObject(obj[1]));
            dto.setAddress(ValueUtil.getStringByObject(obj[2]));
            dto.setTitle(ValueUtil.getStringByObject(obj[3]));
            dto.setNote(ValueUtil.getStringByObject(obj[4]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(authorName, address, title).longValue());
    }

    private BigInteger countSearch(String authorName, String address, String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM author au WHERE 1 = 1 ");
        appendQuery(sb, authorName, address, title);
        Query query = createQuery(sb, authorName, address, title);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.equalsIgnoreCase("NAMEAUTHOR")) {
                sb.append(" au.name_author ");
            } else if (sortField.equalsIgnoreCase("ADDRESS")) {
                sb.append(" au.address ");
            } else if (sortField.equalsIgnoreCase("TITLE")) {
                sb.append(" au.title ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY id_author DESC");
        }
    }


    public void appendQuery(StringBuilder sb, String authorName, String address, String title) {
        if (StringUtils.isNotBlank(authorName)) {
            sb.append(" and au.name_author like :authorName ");
        }
        if (StringUtils.isNotBlank(address)) {
            sb.append(" and au.address like :address ");
        }
        if (StringUtils.isNotBlank(title)) {
            sb.append(" and au.title like :title ");
        }
    }


    public Query createQuery(StringBuilder sb, String authorName, String address, String title) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(authorName)) {
            query.setParameter("authorName", buildFilterLike(authorName));
        }
        if (StringUtils.isNotBlank(address)) {
            query.setParameter("address", buildFilterLike(address));
        }
        if (StringUtils.isNotBlank(title)) {
            query.setParameter("title", buildFilterLike(title));
        }
        return query;
    }

    @Override
    public BigInteger getTotalAuthor() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(a.id_author) as totalAuthor FROM author a where 1 = 1");
        Query query = entityManager.createNativeQuery(sb.toString());
        return (BigInteger) query.getSingleResult();
    }

    @Override
    public List<HomeAuthorResponse> findByAuthor() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT au.id_author," +
                "       au.name_author " +
                "FROM author au " +
                "WHERE 1 = 1  ");
        sb.append(" ORDER BY au.id_author DESC");
        Query query = entityManager.createNativeQuery(sb.toString());
        List<Object[]> result = query.getResultList();

        List<HomeAuthorResponse> HomeAuthorResponse = new ArrayList<>();
        for (Object[] obj : result) {
            HomeAuthorResponse dto = new HomeAuthorResponse();
            dto.setIdAuthor(ValueUtil.getLongByObject(obj[0]));
            dto.setNameAuthor(ValueUtil.getStringByObject(obj[1]));

            HomeAuthorResponse.add(dto);
        }
        return HomeAuthorResponse;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
