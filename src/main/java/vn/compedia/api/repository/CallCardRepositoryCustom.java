package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import vn.compedia.api.entity.CallCardDetails;
import vn.compedia.api.response.MonthDataResponse;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.response.book.CollectMoneyResponse;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface CallCardRepositoryCustom {
    List<CallCardResponse> findAllCustomCallCardList();

    Page<CallCardResponse> search(String cardNumber, Integer status, String nameStaff ,
                                  String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<MonthDataResponse> getAmountBorrow();
}
