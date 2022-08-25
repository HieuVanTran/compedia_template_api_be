package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.BookCategory;
import vn.compedia.api.entity.RequestForm;
import vn.compedia.api.repository.BookCategoryRepository;
import vn.compedia.api.repository.RequestFormRepository;
import vn.compedia.api.request.BookCategoryCreateRequest;
import java.util.List;

@Log4j2
@Service

public class RequestFormService {
    @Autowired
    private RequestFormRepository requestFormRepository;


    public List<RequestForm> getAll() {
        List<RequestForm> list = requestFormRepository.findAll();
        return list;
    }

    public RequestForm getOne(Long cardId) {
        RequestForm requestForm = requestFormRepository.findById(cardId).orElse(null);
        if (requestForm == null) {
            requestForm = new RequestForm();
        }
        return requestForm;
    }

    public void delete(Long id) {

        requestFormRepository.deleteById(id);
    }
}

