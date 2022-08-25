package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CallCardDetails;
import vn.compedia.api.repository.CallCardDetailsRepository;
import vn.compedia.api.response.CallCardDetailsResponse;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service

public class CallCardDetailsService {

    @Autowired
    private CallCardDetailsRepository callCardDetailsRepository;

    public List<CallCardDetailsResponse> getAll() {

        return callCardDetailsRepository.findAllCallCardDetails();
    }


    public CallCardDetailsResponse getOne(Long callCardDetailsId) throws Exception {
        Optional<CallCardDetailsResponse> loan = callCardDetailsRepository.findByIdCallCardDetails(callCardDetailsId);
        if (!loan.isPresent()) {
            throw new Exception("CallCardDetailsId IS EMPTY");
        }
        return loan.get();
    }
}