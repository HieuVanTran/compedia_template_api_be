package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.CollectMoney;

@Repository

public interface CollectMoneyRepository extends JpaRepository<CollectMoney, Long>, CollectMoneyRepositoryCustom {

}
