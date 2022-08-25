package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CollectMoney;
import vn.compedia.api.repository.CollectMoneyRepository;
import vn.compedia.api.request.CollectMoneyCreateRequest;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.book.CollectMoneyResponse;
import java.util.List;

@Log4j2
@Service

public class CollectMoneyService {
    @Autowired
    private CollectMoneyRepository collectMoneyRepository;

    public List<CollectMoneyResponse> getAll() {
        List<CollectMoneyResponse> list = collectMoneyRepository.findAllCollectMoney();
        return list;
    }

    public CollectMoney getOne(Long Id) {
        CollectMoney collectMoney = collectMoneyRepository.findById(Id).orElse(null);
        if (collectMoney == null) {
            collectMoney = new CollectMoney();
        }
        return collectMoney;
    }
    public void validateData(CollectMoneyCreateRequest request) throws Exception {
        if(StringUtils.isBlank(request.getFullName().trim())) {
            throw new Exception ("Fullname không được để trống");
        }
        if(request.getFullName().trim().length() > 50 ) {
            throw new Exception("Độ dài FullName không quá 50 ký tự");
        }

    }
    public void create(CollectMoneyCreateRequest request) throws Exception {
        validateData(request);
        CollectMoney collectMoney = new CollectMoney();
        collectMoney.setUserId(request.getUserId());
        collectMoney.setFullName(request.getFullName());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);
    }

    public void update(CollectMoneyCreateRequest request) throws Exception  {
        validateData(request);
        CollectMoney collectMoney = new CollectMoney();
        collectMoney.setUserId(request.getUserId());
        collectMoney.setCollectMoneyId(request.getId());
        collectMoney.setFullName(request.getFullName());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);

    }

    public void delete(Long id) {

        collectMoneyRepository.deleteById(id);
    }
    public Page<CollectMoneyResponse> search(String fullName, String nameStaff, String sortField, String sortOrder, Integer page, Integer size) {
        return collectMoneyRepository.search(fullName, nameStaff,sortField,sortOrder,page,size, PageRequest.of(page, size));
    }
}

