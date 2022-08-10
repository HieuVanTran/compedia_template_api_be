package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.api.repository.AuthorRepository;
import vn.compedia.api.repository.AuthorRepositoryCustom;
import vn.compedia.api.repository.BookRepositoryCustom;
import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AuthorResponse> search(String authorName, String title) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT au.id_author, au.name_author, au.address, au.title, au.note " +
                "FROM author au WHERE 1 = 1 ");

        appendQuery(sb, authorName, title);
        Query query = createQuery(sb, authorName, title);

        List<AuthorResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            AuthorResponse dto = new AuthorResponse();
            dto.setAuthorId(ValueUtil.getLongByObject(obj[0]));
            dto.setAuthorName(ValueUtil.getStringByObject(obj[1]));
            dto.setTitle(ValueUtil.getStringByObject(obj[2]));
            list.add(dto);
        }

        return list;
    }


    public void appendQuery(StringBuilder sb, String authorName, String title) {
        if (StringUtils.isNotBlank(authorName)) {
            sb.append(" and au.name_author like :authorName ");
        }
        if (StringUtils.isNotBlank(title)) {
            sb.append(" and au.title like :title ");
        }
        sb.append(" order by au.id_author desc ");
    }


    public Query createQuery(StringBuilder sb, String authorName, String title) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(authorName)) {
            query.setParameter("authorName", buildFilterLike(authorName));
        }
        if (StringUtils.isNotBlank(title)) {
            query.setParameter("title", buildFilterLike(title));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
