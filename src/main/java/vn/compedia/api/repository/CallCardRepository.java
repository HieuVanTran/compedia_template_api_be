package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.entity.CallCardDetails;
import vn.compedia.api.entity.CollectMoney;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CallCardRepository extends JpaRepository<CallCard, Long>, CallCardRepositoryCustom {
//    @Query("select ca from CallCard ca where ca.userName like :userName")
//    List<CallCard> findByName(@Param("userName") String userName);

}

