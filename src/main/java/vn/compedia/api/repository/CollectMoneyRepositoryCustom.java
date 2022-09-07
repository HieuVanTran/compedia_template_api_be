package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.book.CollectMoneyResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CollectMoneyRepositoryCustom {


    List<CollectMoneyResponse> findAllCollectMoney();

    Page<CollectMoneyResponse> search(String fullName, Long staffId, String sortField, String username,
                                      String sortOrder, Integer page, Integer size, Pageable pageable);

    BigDecimal getTotalMoney();

    Optional<CollectMoneyResponse> findByIdCollectMoney(Long findByIdCollectMoney);
}
