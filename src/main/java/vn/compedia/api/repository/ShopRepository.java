package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.compedia.api.entity.Shop;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
	Shop findAccountByShopId(Long id);

	Shop findShopByShopId(Long id);

	List<Shop> findLitsShopByAccountId(Long id);

	Shop findShopByAccountId(Long id);
}
