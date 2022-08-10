package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.compedia.api.entity.Author;

import java.util.List;

public interface AdminRepository extends JpaRepository<Author, Long>, AdminRepositoryCustom {
    @Query("select a from Account a where a.username like :username")
    List<Author> findByName(@Param("username")String username);

}
