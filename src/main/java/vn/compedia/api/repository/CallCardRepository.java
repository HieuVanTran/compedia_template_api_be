package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.CallCard;

@Repository
public interface CallCardRepository extends JpaRepository<CallCard, Long>, CallCardRepositoryCustom {
//    @Query("select ca from CallCard ca where ca.userName like :userName")
//    List<CallCard> findByName(@Param("userName") String userName);

}

