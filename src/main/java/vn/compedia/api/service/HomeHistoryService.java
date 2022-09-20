package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.repository.CallCardRepository;
import vn.compedia.api.response.HomeHistoryResponse;


import java.util.List;

@Log4j2
@Service


public class HomeHistoryService {

    @Autowired
    private CallCardRepository callCardRepository;

    public List<HomeHistoryResponse> getAllHistory() throws Exception {
        List<HomeHistoryResponse> history = callCardRepository.findAllHistory();
        if (history.isEmpty()) {
            throw new Exception(" EMPTY ");
        }
        return history;
    }

    public PageImpl<Object> search(String bookName, String sortField, String sortOrder, Integer page, Integer size) {
        return callCardRepository.searchHistory(bookName, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}
