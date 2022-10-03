package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.repository.BookCategoryRepositoryCustom;
import vn.compedia.api.response.book.BookCategoryResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomePageResponse;
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
    public Page<BookCategoryResponse> search(String categoryName, String sortField, String sortOrder,
                                             Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append("select bc.id_type_book, bc.category_name " +
                "from book_category bc " +
                "where 1 = 1");
        appendQuery(sb, categoryName);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, categoryName);

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
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(categoryName).longValue());
    }


    private BigInteger countSearch(String categoryName ) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM book_category bc " +
                "WHERE 1 = 1 ");
        appendQuery(sb, categoryName);
        Query query = createQuery(sb, categoryName);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.equalsIgnoreCase("categoryName")) {
                sb.append(" bc.categoryName ");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY id_type_book DESC");
        }
    }


    public void appendQuery(StringBuilder sb, String categoryName) {
        if (StringUtils.isNotBlank(categoryName)) {
            sb.append(" and bc.category_name like :categoryName ");
        }

    }


    public Query createQuery(StringBuilder sb, String categoryName ) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(categoryName)) {
            query.setParameter("categoryName", buildFilterLike(categoryName));
        }
        return query;
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }

    @Override
    public List<HomeCategoryResponse> findByCategory() {
        StringBuilder sb = new StringBuilder();
        sb.append("select bc.id_type_book," +
                "       bc.category_name " +
                "from book_category bc " +
                " where 1 = 1 ");
        sb.append(" ORDER BY bc.id_type_book DESC");
        Query query = entityManager.createNativeQuery(sb.toString());


        List<HomeCategoryResponse> HomeCategoryResponse = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        for (Object[] obj : result) {
            HomeCategoryResponse dto = new HomeCategoryResponse();
            dto.setIdTypeBook(ValueUtil.getLongByObject(obj[0]));
            dto.setCategoryName(ValueUtil.getStringByObject(obj[1]));
            HomeCategoryResponse.add(dto);
        }
        return HomeCategoryResponse;
    }
}
