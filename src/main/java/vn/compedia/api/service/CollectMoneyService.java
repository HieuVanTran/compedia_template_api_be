package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.CollectMoney;
import vn.compedia.api.entity.Staff;
import vn.compedia.api.repository.CollectMoneyRepository;
import vn.compedia.api.repository.StaffRepository;
import vn.compedia.api.request.CollectMoneyCreateRequest;
import vn.compedia.api.request.StaffCreateRequest;
import vn.compedia.api.util.DateUtil;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Log4j2
@Service

public class CollectMoneyService {
    @Autowired
    private CollectMoneyRepository collectMoneyRepository;
    public List<CollectMoney> getAll() {
        List<CollectMoney> list = collectMoneyRepository.findAll();
        return list;
    }

    public CollectMoney getOne(Long Id) {
        CollectMoney collectMoney = collectMoneyRepository.findById(Id).orElse(null);
        if (collectMoney == null) {
            collectMoney = new CollectMoney();
        }
        return collectMoney;
    }

    public void create(CollectMoneyCreateRequest request) {
        CollectMoney collectMoney = new CollectMoney();
        collectMoney.setFullName(request.getFullName());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);
    }

    public void update(CollectMoneyCreateRequest request) {
        CollectMoney collectMoney = new CollectMoney();
        collectMoney.setCardId(request.getId());
        collectMoney.setFullName(request.getFullName());
        collectMoney.setStaffId(request.getStaffId());
        collectMoney.setFinedAmount(request.getFineAmount());
        collectMoney.setProceeds(request.getProceeds());
        collectMoneyRepository.save(collectMoney);

    }

    public void delete(Long id) {

        collectMoneyRepository.deleteById(id);
    }
}

