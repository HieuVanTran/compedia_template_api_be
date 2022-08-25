package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.entity.CollectMoney;
import vn.compedia.api.response.book.CollectMoneyResponse;
import vn.compedia.api.response.book.PublishCompanyResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CollectMoneyRepositoryCustom {


    List<CollectMoneyResponse> findAllCollectMoney();

    Page<CollectMoneyResponse> search(String fullName, String nameStaff, String sortField,
                                      String sortOrder, Integer page, Integer size, Pageable pageable);

    BigDecimal getTotalMoney();
}
