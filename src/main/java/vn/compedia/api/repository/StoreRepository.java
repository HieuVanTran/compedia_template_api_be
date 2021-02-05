package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Stores;

@Repository
public interface StoreRepository extends JpaRepository<Stores, Long>, StoreRepositoryCustom {

}
