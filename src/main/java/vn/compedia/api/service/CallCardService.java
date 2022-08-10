package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.entity.CallCardDetails;
import vn.compedia.api.repository.CallCardDetailsRepository;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.request.CallCardDetailsRequest;
import vn.compedia.api.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class CallCardService {

    @Autowired
    private CallCardRepository callCardRepository;

    @Autowired
    private CallCardDetailsRepository callCardDetailsRepository;

    public List<CallCard> getAll() {
        return callCardRepository.findAll();
    }

    public CallCard getOne(Long bookId) {
        CallCard loan = callCardRepository.findById(bookId).orElse(null);
        if (loan == null) {
            loan = new CallCard();
        }
        return loan;
    }

    public void delete(Long id) {
        callCardDetailsRepository.deleteAllByCallCardId(id);
        callCardRepository.deleteById(id);
    }

    public void createCallCard(CallCardCreateRequest request) {
        CallCard callCard = new CallCard();
        BeanUtils.copyProperties(request, callCard);
        callCard.setStartDate(DateUtil.formatDatePattern(request.getStartDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(1);
        callCard.setCreateDate(new Date());
        CallCard result = callCardRepository.save(callCard);

        List<CallCardDetails> detailsList = new ArrayList<>();
        for (CallCardDetailsRequest detailsRequest : request.getListBook()) {
            CallCardDetails details = new CallCardDetails();
            details.setBookId(detailsRequest.getBookId());
            details.setAmount(detailsRequest.getAmount());
            details.setCallCardId(result.getCallCardId());
            detailsList.add(details);
        }
        callCardDetailsRepository.saveAll(detailsList);
    }
    public void update(CallCardCreateRequest request) {
        CallCard callCard = new CallCard();
        BeanUtils.copyProperties(request, callCard);
        callCard.setStartDate(DateUtil.formatDatePattern(request.getStartDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setCallCardId(request.getCallCardId());
        callCard.setStatus(1);
        callCard.setCreateDate(new Date());
        callCardRepository.save(callCard);

        List<CallCardDetails> detailsList = new ArrayList<>();
        for (CallCardDetailsRequest detailsRequest : request.getListBook()) {
            CallCardDetails details = new CallCardDetails();
            details.setBookId(detailsRequest.getBookId());
            details.setAmount(detailsRequest.getAmount());
            details.setCallCardId(callCard.getCallCardId());
            details.setCallCardDetailsId(detailsRequest.getCallCardDetailsId());
            detailsList.add(details);
        }
        callCardDetailsRepository.saveAll(detailsList);
    }
}






