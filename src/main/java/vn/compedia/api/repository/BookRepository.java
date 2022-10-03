package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Book;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @Query("select b from Book b where b.bookName like :bookName")
    List<Book> findByName(@Param("bookName") String bookName);

    @Transactional
    @Modifying
    @Query(" update Book b set b.amount = b.amount - ?2  where  b.bookId = ?1 ")
//    @Query(" update Book b set b.amount = b.amount -:amountCC where cc.status = 1 and  b.bookId =:bookId ")
    void updateSubtractAmount(Long bookId, Integer amount);

    @Transactional
    @Modifying
    @Query(" update Book b set b.amount = b.amount + ?2 where b.bookId = ?1 ")
    void updateSumAmount(Long bookId, Integer amount);

    @Query("select b.amount as amountBook from Book b where b.bookId = ?1")
    Optional<Book> findAmountBook(@Param("bookId") Long bookId);
}
