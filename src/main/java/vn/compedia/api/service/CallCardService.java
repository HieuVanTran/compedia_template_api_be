package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.repository.CallCardDetailsRepository;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    public CallCardResponse getOne(Long callCardId) throws Exception {
        Optional<CallCardResponse> loan = callCardRepository.findByIdCallCard(callCardId);
        if (!loan.isPresent()) {
            throw new Exception("CallCardDetailsId IS EMPTY");
        }
        return loan.get();
    }


    public void delete(Long id) {
        callCardRepository.deleteById(id);
    }

    public void validateData(CallCardCreateRequest request) throws Exception {

        if (StringUtils.isBlank(request.getAmount().toString())) {
            throw new Exception("Không được để trống");
        }
        if (StringUtils.isBlank(request.getEndDate().trim())) {
            throw new Exception("Không được để trống");
        }
        if (request.getNote().trim().length() > 65.535) {
            throw new Exception("Vượt quá  ký tự cho phép");
        }


    }

    public void createCallCard(CallCardCreateRequest request) throws Exception {
        validateData(request);
        CallCard callCard = new CallCard();
        callCard.setAccountId(request.getAccountId());
        callCard.setStartDate(new Date());
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(1);
        callCard.setStaffId(request.getStaffId());
        callCard.setBookId(request.getBookId());
        callCard.setAmount(request.getAmount());
        callCard.setNote(request.getNote());
        callCardRepository.save(callCard);

    }

    public void update(CallCardCreateRequest request) throws Exception {
        // Validate data
        validateData(request);
        CallCard callCard = callCardRepository.findById(request.getCallCardId()).get();
        callCard.setAccountId(request.getAccountId());
        callCard.setStartDate(new Date());
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(1);
        callCard.setStaffId(request.getStaffId());
        callCard.setBookId(request.getBookId());
        callCard.setAmount(request.getAmount());
        callCard.setNote(request.getNote());
        callCardRepository.save(callCard);

    }

    public Page<CallCardResponse> search(String username, Integer status, String nameStaff,
                                         String sortField, String sortOrder, Integer page, Integer size) {
        return callCardRepository.search(username, status, nameStaff, sortField, sortOrder, page, size, PageRequest.of(page, size));

    }
}







