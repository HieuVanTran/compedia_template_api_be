package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.repository.AdminRepository;
import vn.compedia.api.repository.AdminRepositoryCustom;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.response.admin.AdminResponse;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;

import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class AccountService {


    private String code;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AdminRepository adminRepository;


    public List<Account> getAll() {
        List<Account> list = accountRepository.findAll();
        return list;
    }

    public Account getOne(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    public void create(AdminCreateRequest request) {
        Account account = new Account();
        account.setSalt(StringUtil.generateSalt());
        account.setPassword(StringUtil.encryptPassword(request.getPassword(), account.getSalt()));
        account.setUsername(request.getUsername());
        account.setRoleId(request.getRoleId());
        account.setCreateDate(new Date());
        account.setCreateBy(UserContextHolder.getUser().getAccountId());
        account.setEmail(request.getEmail());
        account.setFullName(request.getFullName());
        Date dob = DateUtil.formatDatePattern(request.getDOB(), DateUtil.DATE_FORMAT_YEAR);
        account.setDOB(dob);
        account.setPhone(request.getPhone());
        accountRepository.save(account);
    }

    public void update(AdminCreateRequest request) {
        Account account = accountRepository.findById(request.getId()).get();
        account.setSalt(StringUtil.generateSalt());
        account.setPassword(StringUtil.encryptPassword(request.getPassword(), account.getSalt()));
        account.setUsername(request.getUsername());
        account.setRoleId(request.getRoleId());
        account.setCreateDate(new Date());
        account.setCreateBy(UserContextHolder.getUser().getAccountId());
        account.setFullName(request.getFullName());
        Date dob = DateUtil.formatDatePattern(request.getDOB(), DateUtil.DATE_FORMAT_YEAR);
        account.setDOB(dob);
        account.setPhone(request.getPhone());
        accountRepository.save(account);
    }

    public void delete(Long id) {

        accountRepository.deleteById(id);
    }
    public List<AdminResponse> search(String username, String email, String fullName, String roleId) {
        return adminRepository.search(username, email, fullName,roleId );
    }
}


