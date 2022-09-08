package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.*;
import vn.compedia.api.response.DashBoardResponse;
import vn.compedia.api.response.MonthDataResponse;

import java.util.List;

@Log4j2
@Service
public class DashBoardService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CollectMoneyRepository collectMoneyRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublishCompanyRepository publishCompanyRepository;
    @Autowired
    private CallCardRepository callCardRepository;


    public DashBoardResponse getDate() {
        DashBoardResponse response = new DashBoardResponse();
        response.setTotalPublisher(publishCompanyRepository.getTotalPublisher().longValue());
        response.setTotalAuthor(authorRepository.getTotalAuthor().longValue());
        response.setTotalBook(bookRepository.getTotalBook().longValue());
        response.setTotalMoney(collectMoneyRepository.getTotalMoney().longValue());
        return response;
    }

    public List<MonthDataResponse> getMonth(String year) throws Exception {
        List<MonthDataResponse> month = callCardRepository.getAmountBorrow(year);
        if (month == null) {
            throw new Exception(" EMPTY ");
        }
        return month ;
    }
}
