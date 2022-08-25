package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.entity.CallCardDetails;
import vn.compedia.api.repository.CallCardDetailsRepository;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.request.CallCardDetailsRequest;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CallCardService {

    @Autowired
    private CallCardRepository callCardRepository;

    @Autowired
    private CallCardDetailsRepository callCardDetailsRepository;


    public List<CallCardResponse> getAll() {

        return callCardRepository.findAllCustomCallCardList();
    }


    public CallCard getOne(Long callCardId) {
        CallCard loan = callCardRepository.findById(callCardId).orElse(null);
        if (loan == null) {
            loan = new CallCard();
        }
        return loan;
    }

    public void delete(Long id) {
        callCardDetailsRepository.deleteAllByCallCardId(id);
        callCardRepository.deleteById(id);
    }
    public void validateData(CallCardCreateRequest request) throws Exception {


        if (StringUtils.isBlank(request.getCardNumber().trim())) {
            throw new Exception("Không được để trống!");
        }
        if (request.getCardNumber().trim().length() > 50){
            throw new Exception("Vượt quá 50 ký tự, yêu cầu nhập lại fullName");
        }
    }

    public void createCallCard(CallCardCreateRequest request) throws Exception {
        // Validate data
        validateData(request);
        CallCard callCard = new CallCard();
        BeanUtils.copyProperties(request, callCard);
        callCard.setStartDate(DateUtil.formatDatePattern(request.getStartDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(1);
        callCard.setStaffId(request.getStaffId());
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
    public void update(CallCardCreateRequest request) throws Exception {
        // Validate data
        validateData(request);
        CallCard callCard = new CallCard();
        BeanUtils.copyProperties(request, callCard);
        callCard.setStartDate(DateUtil.formatDatePattern(request.getStartDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setCallCardId(request.getCallCardId());
        callCard.setStatus(1);
        callCard.setStaffId(request.getStaffId());
        callCard.setCallCardId(request.getCallCardId());
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
    public Page<CallCardResponse> search(String cardNumber,Integer status,String nameStaff ,
                                         String sortField, String sortOrder, Integer page, Integer size) {
        return callCardRepository.search( cardNumber,status,nameStaff ,sortField,sortOrder,page,size,PageRequest.of(page,size));

    }
}







