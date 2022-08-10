package vn.compedia.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.BookCategory;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    BookCategory findBookCategoryByIdtypeBook(Long id) ;
}
