package vn.compedia.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.Author;
import vn.compedia.api.entity.BookCategory;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryRepositoryCustom {
    @Query("select bc from BookCategory bc where bc.categoryName like :categoryName")
    List<BookCategory> findByName(@Param("categoryName")String categoryName);

}
