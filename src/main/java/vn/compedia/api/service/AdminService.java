package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Account;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.request.AdminCreateRequest;
import vn.compedia.api.util.DateUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;

import java.util.Date;

@Log4j2
@Service
public class AdminService {

    @Value("${vn.compedia.static.context}")
    private String staticContext;
    @Value("${accept_image_file_types}")
    private String acceptImageTypes;

    private String code;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageSource messageSource;

    public void create(AdminCreateRequest request) {
        Account account = new Account();
        account.setSalt(StringUtil.generateSalt());
        account.setPassword(StringUtil.encryptPassword(request.getPassword(), account.getSalt()));
        account.setUsername(request.getUsername());
        account.setRoleId(request.getRoleId());
        account.setCreateDate(new Date());
        account.setCreateBy(UserContextHolder.getUser().getAccountId());
        accountRepository.save(account);

    }

}
