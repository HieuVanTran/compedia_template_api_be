package vn.compedia.api.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import vn.compedia.api.repository.BookRepositoryCustom;
import vn.compedia.api.response.book.BookResponse;
import vn.compedia.api.response.index.HomeCategoryResponse;
import vn.compedia.api.response.index.HomeDetailsResponse;
import vn.compedia.api.response.index.HomePageResponse;
import vn.compedia.api.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BookResponse> getAllBook() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.book_id," +
                "       b.book_name," +
                "       b.status," +
                "       b.amount," +
                "       b.image," +
                "       b.page_number," +
                "       b.publishing_year," +
                "       b.price," +
                "       a.id_author," +
                "       a.name_author," +
                "       bc.id_type_book," +
                "       bc.category_name," +
                "       pc.company_id," +
                "       pc.publish_name, " +
                "       b.note " +
                "" +
                "from book b" +
                "         inner join author a on b.id_author = a.id_author " +
                "         inner join book_category bc on b.id_type_book = bc.id_type_book " +
                "         inner join publish_company pc on b.company_id = pc.company_id " +
                "where 1 = 1");
        sb.append(" ORDER BY b.book_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());


        List<BookResponse> BookResponse = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        for (Object[] obj : result) {
            BookResponse dto = new BookResponse();
            dto.setBookId(ValueUtil.getLongByObject(obj[0]));
            dto.setBookName(ValueUtil.getStringByObject(obj[1]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
            dto.setAmount(ValueUtil.getIntegerByObject(obj[3]));
            dto.setImage(ValueUtil.getStringByObject(obj[4]));
            dto.setPageNumber(ValueUtil.getIntegerByObject(obj[5]));
            dto.setPublishingYear(ValueUtil.getStringByObject(obj[6]));
            dto.setPrice(ValueUtil.getIntegerByObject(obj[7]));
            dto.setIdAuthor(ValueUtil.getLongByObject(obj[8]));
            dto.setNameAuthor(ValueUtil.getStringByObject(obj[9]));
            dto.setIdTypeBook(ValueUtil.getLongByObject(obj[10]));
            dto.setCategoryName(ValueUtil.getStringByObject(obj[11]));
            dto.setCompanyId(ValueUtil.getLongByObject(obj[12]));
            dto.setPublishName(ValueUtil.getStringByObject(obj[13]));
            dto.setNote(ValueUtil.getStringByObject(obj[14]));
            BookResponse.add(dto);
        }
        return BookResponse;
    }

    @Override
    public Page<BookResponse> search(String bookName, String nameAuthor, String categoryName, String publishName,
                                     String sortField, String sortOrder, Integer page, Integer size, Pageable pageable) {

        StringBuilder sb = new StringBuilder();
        sb.append(" select b.book_id," +
                "       b.book_name," +
                "       b.status," +
                "       b.amount," +
                "       b.image," +
                "       b.page_number," +
                "       b.publishing_year," +
                "       b.price," +
                "       a.id_author," +
                "       a.name_author," +
                "       bc.id_type_book," +
                "       bc.category_name," +
                "       pc.company_id," +
                "       pc.publish_name, " +
                "       b.note " +
                "" +
                "from book b" +
                "         inner join author a on b.id_author = a.id_author " +
                "         inner join book_category bc on b.id_type_book = bc.id_type_book " +
                "         inner join publish_company pc on b.company_id = pc.company_id " +
                "where 1 = 1");

        appendQuery(sb, bookName, nameAuthor, categoryName, publishName);
        setSortOrder(sortField, sortOrder, sb);
        Query query = createQuery(sb, bookName, nameAuthor, categoryName, publishName);

        if (pageable.getPageSize() > 0) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<BookResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();

        for (Object[] obj : result) {
            BookResponse dto = new BookResponse();
            dto.setBookId(ValueUtil.getLongByObject(obj[0]));
            dto.setBookName(ValueUtil.getStringByObject(obj[1]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
            dto.setAmount(ValueUtil.getIntegerByObject(obj[3]));
            dto.setImage(ValueUtil.getStringByObject(obj[4]));
            dto.setPageNumber(ValueUtil.getIntegerByObject(obj[5]));
            dto.setPublishingYear(ValueUtil.getStringByObject(obj[6]));
            dto.setPrice(ValueUtil.getIntegerByObject(obj[7]));
            dto.setIdAuthor(ValueUtil.getLongByObject(obj[8]));
            dto.setNameAuthor(ValueUtil.getStringByObject(obj[9]));
            dto.setIdTypeBook(ValueUtil.getLongByObject(obj[10]));
            dto.setCategoryName(ValueUtil.getStringByObject(obj[11]));
            dto.setCompanyId(ValueUtil.getLongByObject(obj[12]));
            dto.setPublishName(ValueUtil.getStringByObject(obj[13]));
            dto.setNote(ValueUtil.getStringByObject(obj[14]));
            list.add(dto);
        }

        return new PageImpl<>(list, pageable, countSearch(bookName, nameAuthor, categoryName, publishName).longValue());
    }

    private BigInteger countSearch(String bookName, String nameAuthor, String categoryName, String publishName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(0) " +
                " FROM book b" +
                " inner join author a on b.id_author = a.id_author  " +
                " inner join book_category bc on b.id_type_book = bc.id_type_book  " +
                " inner join publish_company pc on b.company_id = pc.company_id  " +
                " WHERE 1 = 1 ");
        appendQuery(sb, bookName, nameAuthor, categoryName, publishName);
        Query query = createQuery(sb, bookName, nameAuthor, categoryName, publishName);
        return (BigInteger) query.getSingleResult();
    }

    private void setSortOrder(String sortField, String sortOrder, StringBuilder sb) {
        if (StringUtils.isNotBlank(sortField)) {
            sb.append(" ORDER BY ");
            if (sortField.toLowerCase().equals("bookName")) {
                sb.append(" b.bookName ");
            } else if (sortField.toLowerCase().equals("authorName")) {
                sb.append(" a.authorName ");
            } else if (sortField.toLowerCase().equals("categoryName")) {
                sb.append("bc.categoryName");
            } else if (sortField.toLowerCase().equals("publishName")) {
                sb.append("pc.publishName");
            }
            sb.append(sortOrder);
        } else {
            sb.append(" ORDER BY b.book_id DESC");
        }
    }

    public void appendQuery(StringBuilder sb, String bookName, String nameAuthor, String categoryName, String publishName) {
        if (StringUtils.isNotBlank(bookName)) {
            sb.append(" and b.book_name like :bookName ");
        }
        if (StringUtils.isNotBlank(nameAuthor)) {
            sb.append(" and a.name_author like :nameAuthor ");
        }
        if (StringUtils.isNotBlank(categoryName)) {
            sb.append(" and bc.category_name like :categoryName ");
        }
        if (StringUtils.isNotBlank(publishName)) {
            sb.append(" and pc.publish_name like :publishName ");
        }
    }

    public Query createQuery(StringBuilder sb, String bookName, String nameAuthor, String categoryName, String publishName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(bookName)) {
            query.setParameter("bookName", buildFilterLike(bookName));
        }
        if (StringUtils.isNotBlank(nameAuthor)) {
            query.setParameter("nameAuthor", buildFilterLike(nameAuthor));
        }
        if (StringUtils.isNotBlank(categoryName)) {
            query.setParameter("categoryName", buildFilterLike(categoryName));
        }
        if (StringUtils.isNotBlank(publishName)) {
            query.setParameter("publishName", buildFilterLike(publishName));
        }
        return query;
    }

    @Override
    public BigDecimal getTotalBook() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT SUM(b.amount) as totalBook FROM book b where b.status  = 1");

        Query query = entityManager.createNativeQuery(sb.toString());
        return (BigDecimal) query.getSingleResult();
    }


    private String buildFilterLike(String query) {
        return "%" + query.trim() + "%";
    }

    @Override
    public List<HomePageResponse> findAllHome() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select b.book_id," +
                "       b.book_name," +
                "       b.image," +
                "       a.id_author," +
                "       a.name_author," +
                "       bc.id_type_book," +
                "       bc.category_name," +
                "       pc.company_id," +
                "       pc.publish_name, " +
                "       b.note " +
                "" +
                "from book b" +
                "         inner join author a on b.id_author = a.id_author " +
                "         inner join book_category bc on b.id_type_book = bc.id_type_book " +
                "         inner join publish_company pc on b.company_id = pc.company_id " +
                "where 1 = 1 ");
        sb.append(" ORDER BY b.book_id DESC");
        Query query = entityManager.createNativeQuery(sb.toString());


        List<HomePageResponse> HomePageResponse = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        for (Object[] obj : result) {
            HomePageResponse dto = new HomePageResponse();
            dto.setBookId(ValueUtil.getLongByObject(obj[0]));
            dto.setBookName(ValueUtil.getStringByObject(obj[1]));
            dto.setImage(ValueUtil.getStringByObject(obj[2]));
            dto.setIdAuthor(ValueUtil.getLongByObject(obj[3]));
            dto.setAuthorName(ValueUtil.getStringByObject(obj[4]));
            dto.setIdTypeBook(ValueUtil.getLongByObject(obj[5]));
            dto.setCategoryName(ValueUtil.getStringByObject(obj[6]));
            dto.setCompanyId(ValueUtil.getLongByObject(obj[7]));
            dto.setPublishName(ValueUtil.getStringByObject(obj[8]));
            dto.setNote(ValueUtil.getStringByObject(obj[9]));
            HomePageResponse.add(dto);
        }
        return HomePageResponse;
    }

    @Override
    public List<HomePageResponse> findByHome(Long idTypeBook) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.book_id," +
                "       b.book_name," +
                "       b.image," +
                "       a.id_author," +
                "       a.name_author," +
                "       bc.id_type_book," +
                "       bc.category_name," +
                "       pc.company_id," +
                "       pc.publish_name, " +
                "       b.note " +
                "from book b " +
                "         inner join author a on b.id_author = a.id_author " +
                "         inner join book_category bc on b.id_type_book = bc.id_type_book " +
                "         inner join publish_company pc  on b.company_id = pc.company_id " +
                "where bc.id_type_book = :idTypeBook");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeBook", idTypeBook);
        List<HomePageResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                HomePageResponse dto = new HomePageResponse();
                dto.setBookId(ValueUtil.getLongByObject(obj[0]));
                dto.setBookName(ValueUtil.getStringByObject(obj[1]));
                dto.setImage(ValueUtil.getStringByObject(obj[2]));
                dto.setIdAuthor(ValueUtil.getLongByObject(obj[3]));
                dto.setAuthorName(ValueUtil.getStringByObject(obj[4]));
                dto.setIdTypeBook(ValueUtil.getLongByObject(obj[5]));
                dto.setCategoryName(ValueUtil.getStringByObject(obj[6]));
                dto.setCompanyId(ValueUtil.getLongByObject(obj[7]));
                dto.setPublishName(ValueUtil.getStringByObject(obj[8]));
                dto.setNote(ValueUtil.getStringByObject(obj[9]));
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public List<HomeCategoryResponse> findByCategory(Long idTypeBook, Long idAuthor) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.book_id," +
                "       b.book_name," +
                "       b.image," +
                "       a.id_author," +
                "       a.name_author," +
                "       bc.id_type_book," +
                "       bc.category_name," +
                "       pc.company_id," +
                "       pc.publish_name, " +
                "       b.note " +
                "from book b " +
                "         inner join author a on b.id_author = a.id_author " +
                "         inner join book_category bc on b.id_type_book = bc.id_type_book " +
                "         inner join publish_company pc  on b.company_id = pc.company_id " +
                "where bc.id_type_book = :idTypeBook or a.id_author = :idAuthor");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeBook", idTypeBook);
        query.setParameter("idAuthor", idAuthor);
        List<HomeCategoryResponse> list = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                HomeCategoryResponse dto = new HomeCategoryResponse();
                dto.setBookId(ValueUtil.getLongByObject(obj[0]));
                dto.setBookName(ValueUtil.getStringByObject(obj[1]));
                dto.setImage(ValueUtil.getStringByObject(obj[2]));
                dto.setIdAuthor(ValueUtil.getLongByObject(obj[3]));
                dto.setNameAuthor(ValueUtil.getStringByObject(obj[4]));
                dto.setIdTypeBook(ValueUtil.getLongByObject(obj[5]));
                dto.setCategoryName(ValueUtil.getStringByObject(obj[6]));
                dto.setCompanyId(ValueUtil.getLongByObject(obj[7]));
                dto.setPublishName(ValueUtil.getStringByObject(obj[8]));
                dto.setNote(ValueUtil.getStringByObject(obj[9]));
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public Optional<HomeDetailsResponse> findByDetails(Long bookId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.book_id, +" +
                "                       b.book_name, " +
                "                       b.status, " +
                "                       b.amount, " +
                "                       b.image, " +
                "                       b.page_number, " +
                "                       b.publishing_year, " +
                "                       b.price, " +
                "                       a.id_author, " +
                "                       a.name_author, " +
                "                       bc.id_type_book, " +
                "                       bc.category_name, " +
                "                       pc.company_id, " +
                "                       pc.publish_name,  " +
                "                       b.note  " +
                "                 " +
                "                from book b " +
                "                         inner join author a on b.id_author = a.id_author  " +
                "                         inner join book_category bc on b.id_type_book = bc.id_type_book  " +
                "                         inner join publish_company pc on b.company_id = pc.company_id  " +
                "                where b.book_id = :bookId ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("bookId", bookId);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                HomeDetailsResponse dto = new HomeDetailsResponse();
                dto.setBookId(ValueUtil.getLongByObject(obj[0]));
                dto.setBookName(ValueUtil.getStringByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setAmount(ValueUtil.getIntegerByObject(obj[3]));
                dto.setImage(ValueUtil.getStringByObject(obj[4]));
                dto.setPageNumber(ValueUtil.getIntegerByObject(obj[5]));
                dto.setPublishingYear(ValueUtil.getStringByObject(obj[6]));
                dto.setPrice(ValueUtil.getIntegerByObject(obj[7]));
                dto.setIdAuthor(ValueUtil.getLongByObject(obj[8]));
                dto.setNameAuthor(ValueUtil.getStringByObject(obj[9]));
                dto.setIdTypeBook(ValueUtil.getLongByObject(obj[10]));
                dto.setCategoryName(ValueUtil.getStringByObject(obj[11]));
                dto.setCompanyId(ValueUtil.getLongByObject(obj[12]));
                dto.setPublishName(ValueUtil.getStringByObject(obj[13]));
                dto.setNote(ValueUtil.getStringByObject(obj[14]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
    @Override
    public List<HomeDetailsResponse> findByListDetails(Long idTypeBook) {
        StringBuilder sb = new StringBuilder();
        sb.append("select b.book_id, +" +
                "                       b.book_name, " +
                "                       b.status, " +
                "                       b.amount, " +
                "                       b.image, " +
                "                       b.page_number, " +
                "                       b.publishing_year, " +
                "                       b.price, " +
                "                       a.id_author, " +
                "                       a.name_author, " +
                "                       bc.id_type_book, " +
                "                       bc.category_name, " +
                "                       pc.company_id, " +
                "                       pc.publish_name,  " +
                "                       b.note  " +
                "                 " +
                "                from book b " +
                "                         inner join author a on b.id_author = a.id_author  " +
                "                         inner join book_category bc on b.id_type_book = bc.id_type_book  " +
                "                         inner join publish_company pc on b.company_id = pc.company_id  " +
                "                where bc.id_type_book = :idTypeBook ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeBook", idTypeBook);
        List<Object[]> result = query.getResultList();
        List<HomeDetailsResponse> list  = new ArrayList<>();;
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                HomeDetailsResponse dto = new HomeDetailsResponse();
                dto.setBookId(ValueUtil.getLongByObject(obj[0]));
                dto.setBookName(ValueUtil.getStringByObject(obj[1]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                dto.setAmount(ValueUtil.getIntegerByObject(obj[3]));
                dto.setImage(ValueUtil.getStringByObject(obj[4]));
                dto.setPageNumber(ValueUtil.getIntegerByObject(obj[5]));
                dto.setPublishingYear(ValueUtil.getStringByObject(obj[6]));
                dto.setPrice(ValueUtil.getIntegerByObject(obj[7]));
                dto.setIdAuthor(ValueUtil.getLongByObject(obj[8]));
                dto.setNameAuthor(ValueUtil.getStringByObject(obj[9]));
                dto.setIdTypeBook(ValueUtil.getLongByObject(obj[10]));
                dto.setCategoryName(ValueUtil.getStringByObject(obj[11]));
                dto.setCompanyId(ValueUtil.getLongByObject(obj[12]));
                dto.setPublishName(ValueUtil.getStringByObject(obj[13]));
                dto.setNote(ValueUtil.getStringByObject(obj[14]));
                list.add(dto);
            }
        }
        return list;
    }
}