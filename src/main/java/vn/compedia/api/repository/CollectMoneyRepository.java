package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Book;
import vn.compedia.api.entity.CollectMoney;

import java.util.List;

@Repository

public interface CollectMoneyRepository extends JpaRepository<CollectMoney, Long>, CollectMoneyRepositoryCustom {
    @Query("select c from CollectMoney c where c.fullName like :fullName")
    List<CollectMoney> findByName(@Param("fullName")String fullName);
}
