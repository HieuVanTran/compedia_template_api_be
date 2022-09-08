package vn.compedia.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.compedia.api.response.MonthDataResponse;
import vn.compedia.api.response.book.CallCardResponse;

import java.util.List;
import java.util.Optional;

public interface CallCardRepositoryCustom {
    List<CallCardResponse> findAllCustomCallCardList();

    Page<CallCardResponse> search(String username, Integer status, String nameStaff,
                                  String sortField, String sortOrder, Integer page, Integer size, Pageable pageable);

    List<MonthDataResponse> getAmountBorrow(String year);

    Optional<CallCardResponse> findByIdCallCard(Long callCardId);
}
