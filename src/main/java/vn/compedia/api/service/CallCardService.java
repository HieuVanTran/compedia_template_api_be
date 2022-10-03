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
import vn.compedia.api.request.CallCardTypeCreateRequest;
import vn.compedia.api.response.book.CallCardResponse;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.DbConstant;

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

        if (StringUtils.isBlank(request.getAmount().toString().trim())) {
            throw new Exception("Không được để trống");
        }
        if (StringUtils.isBlank(request.getEndDate().trim())) {
            throw new Exception("Không được để trống");
        }
        if (request.getNote().trim().length() > 65535) {
            throw new Exception("Vượt quá  ký tự cho phép");
        }
        if (request.getBookId() == null) {
            throw new Exception("Không để trống BookId");
        }
        if (request.getStaffId() == null) {
            throw new Exception("Không để trống StaffId");
        }
    }

    public void create(CallCardCreateRequest request) throws Exception {
        validateData(request);
        CallCard callCard = new CallCard();
        callCard.setAccountId(request.getAccountId());
        callCard.setStartDate(new Date());
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(DbConstant.STATUS_APPROVED);
        callCard.setIsAction(DbConstant.ACTION_ACTIVE);
        callCard.setStaffId(request.getStaffId());
        callCard.setBookId(request.getBookId());
        callCard.setAmount(request.getAmount());
        callCard.setNote(request.getNote());
        callCardRepository.save(callCard);

    }

    public void update(CallCardCreateRequest request) throws Exception {
        validateData(request);
        CallCard callCard = callCardRepository.findById(request.getCallCardId()).get();
        callCard.setAccountId(request.getAccountId());
        callCard.setStartDate(new Date());
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        if(request.getStatus() == DbConstant.STATUS_APPROVED){
            callCard.setIsAction(DbConstant.ACTION_ACTIVE);
        }
        else  if (request.getStatus() != DbConstant.STATUS_APPROVED ) {
            callCard.setIsAction(DbConstant.ACTION_EMPTY);
        }
        callCard.setStatus(request.getStatus());
        callCard.setStaffId(request.getStaffId());
        callCard.setBookId(request.getBookId());
        callCard.setAmount(request.getAmount());
        callCard.setNote(request.getNote());
        callCardRepository.save(callCard);

    }



    public Page<CallCardResponse> search(String username, Integer status, Integer isTitle, String nameStaff,
                                         String sortField, String sortOrder, Integer page, Integer size) {
        return callCardRepository.search(username, status, isTitle, nameStaff, sortField, sortOrder, page, size, PageRequest.of(page, size));

    }

    public void updateIsAction(CallCardTypeCreateRequest request) {
        CallCard callCard = callCardRepository.findById(request.getCallCardId()).get();
        if(request.getType() == 1 ) {
            callCard.setIsAction(DbConstant.ACTION_PAID);
            callCardRepository.save(callCard);
        } else if (request.getType() == 2){
            callCard.setIsAction(DbConstant.ACTION_TRANSGRESSION);
            callCardRepository.save(callCard);
        }
    }
}







