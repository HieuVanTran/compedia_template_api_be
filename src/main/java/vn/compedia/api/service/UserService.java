package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.Account;
import vn.compedia.api.entity.User;
import vn.compedia.api.repository.UserRepository;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.book.AuthorResponse;
import vn.compedia.api.response.book.UserResponse;
import vn.compedia.api.util.DateUtil;

import java.util.Date;
import java.util.List;

@Log4j2
@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    private MessageSource messageSource;

    public List<User> getAll() {
        List<User> list = userRepository.findAll();
        return list;
    }

    public User getOne(Long accountId) {
        User user = userRepository.findById(accountId).orElse(null);
        if (user == null) {
            user = new User();
        }
        return user;
    }
    public void create(UserCreateRequest request) {
        User user = new User();
        user.setAddress(request.getAddress());
        user.setCardNumber(request.getCardNumber());
        user.setAccountId(request.getAccountId());
        user.setFullName(request.getFullName());
        user.setStaffId(request.getStaffId());
        user.setCreateDate(new Date());
        Date ed = DateUtil.formatDatePattern(request.getExpirationDate(), DateUtil.DATE_FORMAT_YEAR);
        user.setExpirationDate(ed);
        userRepository.save(user);
    }

    public void update(UserCreateRequest request) {
        User user = new User();
        user.setCardId(request.getId());
        user.setAddress(request.getAddress());
        user.setCardNumber(request.getCardNumber());
        user.setAccountId(request.getAccountId());
        user.setFullName(request.getFullName());
        user.setStaffId(request.getStaffId());
        user.setCreateDate(new Date());
        Date ed = DateUtil.formatDatePattern(request.getExpirationDate(), DateUtil.DATE_FORMAT_YEAR);
        user.setExpirationDate(ed);
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponse> search(String fullName) {
        return userRepository.search(fullName);
    }
}