package vn.compedia.api.repository;

import vn.compedia.api.response.CallCardDetailsResponse;

import java.util.List;
import java.util.Optional;

public interface CallCardDetailsRepositoryCustom {
    List<CallCardDetailsResponse> findAllCallCardDetails();

    Optional<CallCardDetailsResponse> findByIdCallCardDetails(Long callCardDetails);


}
