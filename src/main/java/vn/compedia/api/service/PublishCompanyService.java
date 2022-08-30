package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.PublishCompany;
import vn.compedia.api.repository.PublishCompanyRepository;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.request.PublishCompanyCreateRequest;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.book.PublishCompanyResponse;
import vn.compedia.api.util.StringUtil;

import java.util.List;

@Log4j2
@Service
public class PublishCompanyService {

    @Autowired
    private PublishCompanyRepository publishCompanyRepository;

    public List<PublishCompanyResponse> getAll() {
        List<PublishCompanyResponse> list = publishCompanyRepository.getAllPublishCompany();
        return list;
    }

    public PublishCompany getOne(Long Id) {
        PublishCompany publishCompany = publishCompanyRepository.findById(Id).orElse(null);
        if (publishCompany == null) {
            publishCompany = new PublishCompany();
        }
        return publishCompany;
    }
    public void validateData(PublishCompanyCreateRequest request) throws Exception {
        if (!StringUtil.validateEmail(request.getEmail())) {
            throw new Exception("Địa chỉ email không đúng định dạng");
        }
        if (StringUtils.isBlank(request.getPublishName().trim())) {
            throw new Exception("Không được để trống!");
        }
        if(request.getPublishName().trim().length()>50){
            throw new Exception("Nhập lại PublishName có độ dài < 50 ký tự");
        }

        if (StringUtils.isBlank(request.getAddress().trim())) {
            throw  new Exception("Không được để trống");
        }
        if(request.getAddress().trim().length() > 50){
            throw new Exception("Yêu cầu nhập lại Address không quá 50 ký tự");
        }
        if(StringUtils.isBlank(request.getEmail().trim())){
            throw new Exception("Không được để trống");
        }
        if(request.getEmail().trim().length() > 100){
            throw new Exception("Yêu cầu nhập lại Email có độ dài không quá 100 ký tự");
        }
        if (StringUtils.isBlank(request.getAgentPeople().trim())) {
            throw new Exception ("AgentPeople không được để trống");
        }
        if(request.getAgentPeople().trim().length() > 100){
            throw new Exception("Yêu cầu nhập lại AgentPeople có độ dài không quá 100 ký tự");

        }
    }
    public void create(PublishCompanyCreateRequest request) throws Exception {
        validateData(request);
        PublishCompany publishCompany = new PublishCompany();
        publishCompany.setPublishName(request.getPublishName());
        publishCompany.setAddress(request.getAddress());
        publishCompany.setEmail(request.getEmail());
        publishCompany.setAgentPeople(request.getAgentPeople());
//        Date df = DateUtil.formatDatePattern(request.getDateFounding(), DateUtil.DATE_FORMAT_YEAR);
//        publishCompany.setDateFounding(df);
        publishCompany.setDateFounding(request.getDateFounding());
        publishCompanyRepository.save(publishCompany);
    }

    public void update(PublishCompanyCreateRequest request) throws Exception {
        validateData(request);
        PublishCompany publishCompany = new PublishCompany();
        publishCompany.setIdPub(request.getId());
        publishCompany.setPublishName(request.getPublishName());
        publishCompany.setAddress(request.getAddress());
        publishCompany.setEmail(request.getEmail());
        publishCompany.setAgentPeople(request.getAgentPeople());
//        Date df = DateUtil.formatDatePattern(request.getDateFounding(), DateUtil.DATE_FORMAT_YEAR);
//        publishCompany.setDateFounding(df);
        publishCompany.setDateFounding(request.getDateFounding());
        publishCompanyRepository.save(publishCompany);
    }

    public void delete(Long id) {

        publishCompanyRepository.deleteById(id);
    }

    public Page<PublishCompanyResponse> search (String publishName, String email, String agentPeople,
                                               String sortField, String sortOrder, Integer page, Integer size)  {
        return publishCompanyRepository.search(publishName, email, agentPeople,sortField,sortOrder,page,size, PageRequest.of(page, size));

    }
}
