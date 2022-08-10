package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.api.entity.Book;
import vn.compedia.api.repository.BookRepositoryCustom;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookResponse> search(String bookName, String authorName) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select b.book_id,  " +
                "       b.book_name, " +
                "       a.id_author,  " +
                "       a.name_author " +
                "from book b " +
                "         inner join author a on b.id_author = a.id_author " +
                "where 1 = 1 ");

        appendQuery(sb, bookName, authorName);
        Query query = createQuery(sb, bookName, authorName);

        List<BookResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            BookResponse dto = new BookResponse();
            dto.setBookId(ValueUtil.getLongByObject(obj[0]));
            dto.setBookName(ValueUtil.getStringByObject(obj[1]));
            dto.setAuthorId(ValueUtil.getLongByObject(obj[2]));
            dto.setAuthorName(ValueUtil.getStringByObject(obj[3]));
            list.add(dto);
        }

        return list;
    }


    public void appendQuery(StringBuilder sb, String bookName, String authorName) {
        if (StringUtils.isNotBlank(bookName)) {
            sb.append(" and b.book_name like :bookName ");
        }
        if (StringUtils.isNotBlank(authorName)) {
            sb.append(" and a.name_author like :authorName ");
        }
        sb.append(" order by b.book_id desc ");
    }


    public Query createQuery(StringBuilder sb, String bookName, String authorName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(bookName)) {
            query.setParameter("bookName", buildFilterLike(bookName));
        }
        if (StringUtils.isNotBlank(authorName)) {
            query.setParameter("authorName", buildFilterLike(authorName));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
