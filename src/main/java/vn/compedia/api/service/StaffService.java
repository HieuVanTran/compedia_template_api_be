
package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.Staff;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.repository.StaffRepository;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.request.StaffCreateRequest;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;

import java.util.Date;
import java.util.List;

@Log4j2
@Service

public class StaffService {

    private String code;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private MessageSource messageSource;
    private Staff staff;

    public List<Staff> getAll() {
        List<Staff> list = staffRepository.findAll();
        return list;
    }

    public Staff getOne(Long Id) {
        Staff staff = staffRepository.findById(Id).orElse(null);
        if (staff == null) {
            staff = new Staff();
        }
        return staff;
    }

    public void create(StaffCreateRequest request) {
        Staff staff = new Staff();
        staff.setAddress(request.getAddress());
        staff.setNameStaff(request.getNameStaff());
        staff.setPhoneNumber(request.getPhoneNumber());
        Date dob = DateUtil.formatDatePattern(request.getDateOfBirth(), DateUtil.DATE_FORMAT_YEAR);
        staff.setDateOfBirth(dob);
        staffRepository.save(staff);
    }

    public void update(StaffCreateRequest request) {
        Staff staff = new Staff();
        staff.setStaffId(request.getId());
        staff.setAddress(request.getAddress());
        staff.setNameStaff(request.getNameStaff());
        staff.setPhoneNumber(request.getPhoneNumber());
        Date dob = DateUtil.formatDatePattern(request.getDateOfBirth(), DateUtil.DATE_FORMAT_YEAR);
        staff.setDateOfBirth(dob);
        staffRepository.save(staff);

    }

    public void delete(Long id) {

        staffRepository.deleteById(id);
    }
}

