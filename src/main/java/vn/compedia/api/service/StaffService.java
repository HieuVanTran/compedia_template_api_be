package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sun.security.validator.ValidatorException;
import vn.compedia.api.entity.Staff;
import vn.compedia.api.repository.StaffRepository;
import vn.compedia.api.request.StaffCreateRequest;
import vn.compedia.api.response.admin.StaffResponse;
import vn.compedia.api.util.DateUtil;

import java.util.List;

@Log4j2
@Service

public class StaffService {

    @Value("${validate.phone_number.regex}")
    private String phoneNumberRegex;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private MessageSource messageSource;

    private Staff staff;

    public List<StaffResponse> getAll() {
        List<StaffResponse> list = staffRepository.getAllStaff();
        return list;
    }

    public Staff getOne(Long Id) {
        Staff staff = staffRepository.findById(Id).orElse(null);
        if (staff == null) {
            staff = new Staff();
        }
        return staff;
    }

    public void validateData(StaffCreateRequest request) throws Exception {
        if (StringUtils.isBlank(request.getNameStaff().trim())) {
            throw new Exception("NameStaff không được để trống");
        }
        if (request.getNameStaff().trim().length() > 50) {
            throw new Exception("Độ dài FullName không quá 50 ký tự");
        }
        if (StringUtils.isBlank(request.getPhoneNumber().trim())) {
            throw new Exception("PhoneNumber không được để trống");
        }
        if (request.getPhoneNumber().trim().length() > 11) {
            throw new Exception("Độ dài PhoneNumber không quá 11 ký tự");
        }
        if (!request.getPhoneNumber().trim().matches(phoneNumberRegex)) {
            throw new Exception("Không đúng định dạng phoneNumber");
        }
        if (StringUtils.isBlank(request.getAddress().trim())) {
            throw new Exception("Không được để trống Address");
        }
        if (request.getAddress().trim().length() > 50) {
            throw new Exception("Độ dài Address không vượt quá 50 ký tự");
        }
        if (StringUtils.isNotBlank(request.getDateOfBirth().trim())) {
            if (!DateUtil.isValid(request.getDateOfBirth())) {
                throw new ValidatorException("birthday is not in the correct format (yyyy-MM-dd)");
            }
    }}

    public void create(StaffCreateRequest request) throws Exception {
        validateData(request);
        Staff staff = new Staff();
        staff.setAddress(request.getAddress());
        staff.setNameStaff(request.getNameStaff());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setDateOfBirth(request.getDateOfBirth());
        staffRepository.save(staff);
    }

    public void update(StaffCreateRequest request) throws Exception {
        validateData(request);
        Staff staff = new Staff();
        staff.setStaffId(request.getId());
        staff.setAddress(request.getAddress());
        staff.setNameStaff(request.getNameStaff());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setDateOfBirth(request.getDateOfBirth());
        staffRepository.save(staff);

    }

    public void delete(Long id) {

        staffRepository.deleteById(id);
    }

    public Page<StaffResponse> search(Long staffId, String phoneNumber, String address,
                                      String sortField, String sortOrder, Integer page, Integer size) {
        return staffRepository.search(staffId, phoneNumber, address, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}


