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
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.repository.AdminRepository;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.response.admin.AccountNeResponse;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AccountService {


    @Value("${validate.phone_number.regex}")
    private String phoneNumberRegex;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AdminRepository adminRepository;

    public List<AccountNeResponse> getAll() {
        return accountRepository.getAllAccount();
    }

    public AccountNeResponse getOne() throws Exception {
        Optional<AccountNeResponse> admin = accountRepository.findByAccountId();
        if (!admin.isPresent()) {
            throw new Exception(" EMPTY");
        }
        return admin.get();
    }

    public void validateData(AdminCreateRequest request) throws Exception {
        if (!StringUtil.validateEmail(request.getEmail())) {
            throw new Exception("Địa chỉ email không đúng định dạng");
        }

        Optional<Account> optionalAccount = findAccountByEmailAndUserName(request.getEmail(), request.getUsername());
        if (optionalAccount.isPresent()) {
            throw new Exception("Tài khoản đã tồn tại");
        }
        if (!StringUtil.validatePassword(request.getPassword())) {
            throw new Exception("Password không đúng định dạng, mật khẩu phải có tám ký tự bao gồm một ký tự hoa, một ký tự đặc biệt và các ký tự chữ và số");
        }
        if (!StringUtil.validateUser(request.getUsername())) {
            throw new Exception("User không đúng định dạng");
        }
        if (!StringUtil.isValid(request.getUsername())) {
            throw new Exception("Nhập lại User có độ dài từ 5-30 ký tự");
        }
        if (StringUtils.isBlank(request.getFullName().trim())) {
            throw new Exception("Không được để trống FullName!");
        }
        if (request.getFullName().trim().length() > 50) {
            throw new Exception("Vượt quá 50 ký tự, yêu cầu nhập lại fullName");
        }
        if (StringUtils.isBlank(request.getPhone().trim())) {
            throw new Exception("Không được để trống PhoneNumber");
        }
        if (request.getPhone().trim().length() > 11) {
            throw new Exception("Yêu cầu nhập lại phoneNumber không quá 11 ký tự");
        }
        if (!request.getPhone().trim().matches(phoneNumberRegex)) {
            throw new Exception("Không đúng định dạng phoneNumber");
        }
        if (StringUtils.isBlank(request.getEmail().trim())) {
            throw new Exception("Không được để trống Email");
        }
        if (request.getEmail().trim().length() > 100) {
            throw new Exception("Yêu cầu nhập lại Email có độ dài không quá 100 ký tự");
        }
        if (request.getRoleId() == null) {
            throw new Exception("Error 01010101");
        }
        if (StringUtils.isNotBlank(request.getDOB().trim())) {
            if (!DateUtil.isValid(request.getDOB())) {
                throw new ValidatorException("birthday is not in the correct format (yyyy-MM-dd)");
            }
        }
        }


    public void create(AdminCreateRequest request) throws Exception {
        validateData(request);
        Account account = new Account();
        account.setPassword(StringUtil.encryptPassword(request.getPassword(), account.getSalt()));
        account.setUsername(request.getUsername());
        account.setRoleId(request.getRoleId());
        account.setCreateDate(new Date());
        account.setCreateBy(UserContextHolder.getUser().getAccountId());
        account.setEmail(request.getEmail());
        account.setFullName(request.getFullName());
        account.setDOB(request.getDOB());
        account.setPhone(request.getPhone());
        accountRepository.save(account);
    }

    public void update(AdminCreateRequest request) throws Exception {
        validateData2(request);
        Account account = accountRepository.findById(request.getId()).get();
        account.setPassword(StringUtil.encryptPassword(request.getPassword(), account.getSalt()));
        account.setUsername(request.getUsername());
        account.setRoleId(request.getRoleId());
        account.setUpdateDate(new Date());
        account.setCreateBy(UserContextHolder.getUser().getAccountId());
        account.setFullName(request.getFullName());
        account.setDOB(request.getDOB());
        account.setPhone(request.getPhone());
        accountRepository.save(account);
    }

    public void validateData2(AdminCreateRequest request) throws Exception {
        if (!StringUtil.validateEmail(request.getEmail())) {
            throw new Exception("Địa chỉ email không đúng định dạng");
        }

        if (!StringUtil.validatePassword(request.getPassword())) {
            throw new Exception("Password không đúng định dạng, mật khẩu phải có tám ký tự bao gồm một ký tự hoa, một ký tự đặc biệt và các ký tự chữ và số");
        }
        if (!StringUtil.validateUser(request.getUsername())) {
            throw new Exception("User không đúng định dạng");
        }
        if (!StringUtil.isValid(request.getUsername())) {
            throw new Exception("Nhập lại User có độ dài từ 5-30 ký tự");
        }
        if (StringUtils.isBlank(request.getFullName().trim())) {
            throw new Exception("Không được để trống!");
        }
        if (request.getFullName().trim().length() > 50) {
            throw new Exception("Vượt quá 50 ký tự, yêu cầu nhập lại fullName");
        }
        if (StringUtils.isBlank(request.getPhone().trim())) {
            throw new Exception("Không được để trống");
        }
        if (request.getPhone().trim().length() > 11) {
            throw new Exception("Yêu cầu nhập lại phoneNumber không quá 11 ký tự");
        }
        if (StringUtils.isBlank(request.getEmail().trim())) {
            throw new Exception("Không được để trống");
        }
        if (request.getEmail().trim().length() > 100) {
            throw new Exception("Yêu cầu nhập lại Email có độ dài không quá 100 ký tự");
        }
    }

    public void delete(Long id) {

        accountRepository.deleteById(id);
    }

    public Page<AdminResponse> search(String username, String email, Integer roleId, String codeRole, String fullName, String sortField, String sortOrder, Integer page, Integer size) {
        return adminRepository.search(username, email, roleId, codeRole, fullName, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }

    private Optional<Account> findAccountByEmailAndUserName(String email, String userName) {
        return accountRepository.findAccountByEmailAndUserName(email, userName);
    }
}


