package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CollectMoney;
import vn.compedia.api.repository.CollectMoneyRepository;
import vn.compedia.api.request.CollectMoneyCreateRequest;
import vn.compedia.api.response.book.CollectMoneyResponse;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service

public class CollectMoneyService {
    @Autowired
    private CollectMoneyRepository collectMoneyRepository;

    public List<CollectMoneyResponse> getAll() {
        List<CollectMoneyResponse> list = collectMoneyRepository.findAllCollectMoney();
        return list;
    }

    public CollectMoneyResponse getOne(Long collectMoneyId) throws Exception {
        Optional<CollectMoneyResponse> money = collectMoneyRepository.findByIdCollectMoney(collectMoneyId);
        if (!money.isPresent()) {
            throw new Exception("CollectMoneyId IS EMPTY");
        }
        return money.get();
    }



    public void create(CollectMoneyCreateRequest request) {

        CollectMoney collectMoney = new CollectMoney();
        collectMoney.setAccountId(request.getAccountId());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);
    }

    public void update(CollectMoneyCreateRequest request) {
        CollectMoney collectMoney = collectMoneyRepository.findById(request.getId()).get();
        collectMoney.setAccountId(request.getAccountId());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);

    }

    public void delete(Long id) {

        collectMoneyRepository.deleteById(id);
    }

    public Page<CollectMoneyResponse> search(String fullName, Long staffId, String username, String sortField, String sortOrder, Integer page, Integer size) {
        return collectMoneyRepository.search(fullName,  staffId, username, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}

