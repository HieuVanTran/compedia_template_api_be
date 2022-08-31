package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Author;

import java.util.List;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, AuthorRepositoryCustom {
    @Query("select au from Author au where au.nameAuthor like :nameAuthor")
    List<Author> findByName(@Param("nameAuthor") String nameAuthor);

}
