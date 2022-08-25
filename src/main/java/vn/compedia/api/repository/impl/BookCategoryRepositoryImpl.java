package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.BookCategoryRepositoryCustom;
import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.response.book.BookCategoryResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BookCategoryRepositoryImpl implements BookCategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<BookCategoryResponse> search(String categoryName, String bookName, String sortField, String sortOrder,
                                             Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("select bc.id_type_book, bc.category_name, b.book_name " +
                "from book_category bc " +
                "inner join book b on bc.id_type_book = b.id_type_book " +
                "where 1 = 1");

        appendQuery(sb, categoryName, bookName);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, categoryName, bookName);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<BookCategoryResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            BookCategoryResponse dto = new BookCategoryResponse();
            dto.setIdTypeBook(ValueUtil.getLongByObject(obj[0]));
            dto.setCategoryName(ValueUtil.getStringByObject(obj[1]));
            dto.setBookName(ValueUtil.getStringByObject(obj[2]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(categoryName, bookName).longValue());
    }


    private BigInteger countSearch(String categoryName, String bookName  ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM book_category bc WHERE 1 = 1 ");
        appendQuery(sb, categoryName, bookName);
        Query query = createQuery(sb, categoryName, bookName);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("categoryName")) {
                sb.append(" bc.categoryName ");
            }
            else if (sortField.toLowerCase().equals("bookName")) {
                sb.append(" b.bookName ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY id_type_book DESC");
        }
    }


    public void appendQuery(StringBuilder sb, String categoryName, String bookName) {
        if (StringUtils.isNotBlank(categoryName)) {
            sb.append(" and bc.category_name like :categoryName ");
        }
        if (StringUtils.isNotBlank(bookName)) {
            sb.append(" and b.book_name like :bookName ");
        }
    }


    public Query createQuery(StringBuilder sb, String categoryName, String bookName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(categoryName)) {
            query.setParameter("categoryName", buildFilterLike(categoryName));
        }
        if (StringUtils.isNotBlank(bookName)) {
            query.setParameter("bookName", buildFilterLike(bookName));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }
}
