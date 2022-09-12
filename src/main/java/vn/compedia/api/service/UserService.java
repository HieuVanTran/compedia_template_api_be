package vn.compedia.api.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.compedia.api.entity.User;
import vn.compedia.api.repository.UserRepository;
import vn.compedia.api.request.UserCreateRequest;
import vn.compedia.api.response.user.UserResponse;

import java.util.List;

@Log4j2
@Service

public class UserService {
    @Value("${validate.phone_number.regex}")
    private String phoneNumberRegex;

    @Autowired
    private UserRepository userRepository;

    private MessageSource messageSource;

    public List<UserResponse> getAll() {
        List<UserResponse> list = userRepository.getAllUser();
        return list;
    }

    public User getOne(Long accountId) {
        User user = userRepository.findById(accountId).orElse(null);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void validateData(UserCreateRequest request) throws Exception {
        if (StringUtils.isBlank(request.getFullName().trim())) {
            throw new Exception("Fullname không được để trống");
        }
        if (request.getFullName().trim().length() > 50) {
            throw new Exception("Độ dài FullName không quá 50 ký tự");
        }
        if (StringUtils.isBlank(request.getPhone().trim())) {
            throw new Exception("PhoneNumber không được để trống");
        }
        if (request.getPhone().trim().length() > 11) {
            throw new Exception("Độ dài PhoneNumber không quá 11 ký tự");
        }
        if (!request.getPhone().trim().matches(phoneNumberRegex)) {
            throw new Exception("Không đúng định dạng phoneNumber");
        }
        if (StringUtils.isBlank(request.getAddress().trim())) {
            throw new Exception("Không được để trống Address");
        }
        if (request.getAddress().trim().length() > 50) {
            throw new Exception("Độ dài Address không vượt quá 50 ký tự");
        }
    }

    public void create(UserCreateRequest request) throws Exception {
        validateData(request);
        User user = new User();
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setCardNumber(request.getCardNumber());
        user.setAccountId(request.getAccountId());
        user.setFullName(request.getFullName());
        user.setCreateDate(request.getCreateDate());
        user.setExpirationDate(request.getExpirationDate());
        userRepository.save(user);
    }

    public void update(UserCreateRequest request) throws Exception {
        validateData(request);
        User user = new User();
        user.setUserId(request.getId());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setCardNumber(request.getCardNumber());
        user.setAccountId(request.getAccountId());
        user.setFullName(request.getFullName());
        user.setCreateDate(request.getCreateDate());
        user.setExpirationDate(request.getExpirationDate());
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Page<UserResponse> search(String fullName, String address, String phone,
                                     String sortField, String sortOrder, Integer page, Integer size) {
        return userRepository.search(fullName, address, phone, sortField, sortOrder, page, size, PageRequest.of(page, size));
    }
}