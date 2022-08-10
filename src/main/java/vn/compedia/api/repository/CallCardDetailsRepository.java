package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.entity.CallCardDetails;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CallCardDetailsRepository extends JpaRepository<CallCardDetails, Long> {

    @Query(value = "delete FROM call_card_details where call_card_id = ?1", nativeQuery = true)
    @Transactional
    void deleteAllByCallCardId(Long id);

}

