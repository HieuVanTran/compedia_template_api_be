package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CallCard;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.request.CallCardCreateRequest;
import vn.compedia.api.request.HomeRequestFromRequest;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.user.UserContextHolder;

import java.awt.dnd.DnDConstants;
import java.util.Date;

@Log4j2
@Service

public class HomeRequestService {

    @Autowired
    private CallCardRepository callCardRepository;




    public void validateData(HomeRequestFromRequest request) throws Exception {

        if (null == request.getAmount()) {
            throw new Exception("Không được để trống");
        }
        if (null == request.getBookId()) {
            throw new Exception("Không được để trống BookId");
        }
        if (request.getNote().trim().length() > 65535) {
            throw new Exception("Vượt quá  ký tự cho phép");
        }
    }

    public void create (HomeRequestFromRequest request) throws Exception {
        validateData(request);
        CallCard callCard = new CallCard();
        callCard.setAccountId(UserContextHolder.getUser().getAccountId());
        callCard.setStartDate(new Date());
        callCard.setEndDate(DateUtil.formatDatePattern(request.getEndDate(), DateUtil.DATE_FORMAT_YEAR));
        callCard.setStatus(DbConstant.STATUS_WAITING);
        callCard.setIsAction(DbConstant.ACTION_EMPTY);
        callCard.setBookId(request.getBookId());
        callCard.setAmount(request.getAmount());
        callCard.setNote(request.getNote());
        callCardRepository.save(callCard);

    }
}
