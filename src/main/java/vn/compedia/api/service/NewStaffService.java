package vn.compedia.api.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.NewStaff;
import vn.compedia.api.repository.NewStaffRepository;
import vn.compedia.api.request.NewStaffRequest;
import vn.compedia.api.response.NewStaffResponse;
import vn.compedia.api.util.DateUtil;

import java.util.Date;
import java.util.List;

@Service
public class NewStaffService {

    @Autowired
    private NewStaffRepository newStaffRepository;

    public List<NewStaff> getAll() {
        return newStaffRepository.findAll();
    }

    public NewStaff getOne(Long Id) {
        NewStaff newstaff = newStaffRepository.findById(Id).orElse(null);
        if (newstaff == null) {
            newstaff = new NewStaff();
        }
        return newstaff;
    }

    public void validateData(NewStaffRequest request) throws Exception {
        if (StringUtils.isBlank(request.getCodeStaff().trim())) {
            throw new Exception("Vui lòng không được để trống trường mã nhân viên");
        }
        if (StringUtils.isBlank(request.getNameStaff().trim())) {
            throw new Exception("Vui lòng không được để trống trường tên nhân viên");
        }
        if (request.getNameStaff().trim().length() > 50) {
            throw new Exception("Tên nhân viên không được quá 50 ký tự");
        }
        if (StringUtils.isBlank(request.getPhone().trim())) {
            throw new Exception("Vui lòng không được để trống trường số điện thoại");
        }
        if (request.getPhone().trim().length() > 10) {
            throw new Exception("Số điện thoại không được quá 10 số");
        }
    }

    public void create(NewStaffRequest request) throws Exception {
        validateData(request);
        NewStaff ns = new NewStaff();

        ns.setId(null);
        ns.setCodeStaff(request.getCodeStaff());
        ns.setNameStaff(request.getNameStaff());

        ns.setDateOfBirth(DateUtil.formatDatePattern(request.getDateStaff(), DateUtil.DDMMYYYY));

        ns.setSex(request.getSexStaff());
        ns.setEmail(request.getEmail());
        ns.setPhone(request.getPhone());
        ns.setAddress(request.getAddress());
        ns.setPosition(request.getPosition());
        ns.setDepartment(request.getDepartment());
        ns.setStatus(request.getStatus());
        ns.setCreateDate(new Date());
        ns.setCreateBy(request.getCreator());
        newStaffRepository.save(ns);
    }

    public void update(NewStaffRequest request) throws Exception {
        validateData(request);
        NewStaff ns = new NewStaff();
        ns.setId(request.getId());
        ns.setCodeStaff(request.getCodeStaff());
        ns.setNameStaff(request.getNameStaff());

        ns.setDateOfBirth(DateUtil.formatDatePattern(request.getDateStaff(), DateUtil.DDMMYYYY));

        ns.setSex(request.getSexStaff());
        ns.setEmail(request.getEmail());
        ns.setPhone(request.getPhone());
        ns.setAddress(request.getAddress());
        ns.setPosition(request.getPosition());
        ns.setDepartment(request.getDepartment());
        ns.setStatus(request.getStatus());
        ns.setCreateDate(new Date());
        ns.setCreateBy(request.getCreator());
        newStaffRepository.save(ns);
    }

    public void delete(Long id) {
        newStaffRepository.deleteById(id);
    }

    public Page<NewStaffResponse> search(String keyword, String sortField, String sortOrder, Integer page, Integer size) {
        return newStaffRepository.search(keyword, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}

