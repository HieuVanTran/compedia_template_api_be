package vn.compedia.api.service;

import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.repository.AccountRepository;
import vn.compedia.api.request.AccountRequest;
import vn.compedia.api.response.AccountResponse;
import vn.compedia.api.util.EmailUtil;
import vn.compedia.api.util.MessageUtil;
import vn.compedia.api.util.StringUtil;
import vn.compedia.api.util.user.UserContextHolder;
import vn.compedia.api.utility.FileUtil;
import vn.compedia.api.utility.PropertiesUtil;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class AccountService {

	@Value("${vn.compedia.static.context}")
	private String staticContext;
	@Value("${accept_image_file_types}")
	private String acceptImageTypes;

	private String code;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MessageSource messageSource;

	public Account detail(Long id) throws VietTienNotFoundException {
		Account account = accountRepository.findById(id).orElse(null);

		// validate
		if (account == null) {
			throw new VietTienNotFoundException(Account.class);
		}

		account.setImages(StringUtil.buildURI(staticContext, account.getImages()));
		return account;
	}

	public boolean getStatusUpdate(Long id) throws VietTienNotFoundException {

		Optional<Account> account = accountRepository.findById(id);
		if (!account.isPresent()) {
			throw new VietTienNotFoundException(Account.class);
		}

		return account.get().isFirstLogin();
	}

	public void onCheckEmail(Integer language, String email) throws VietTienInvalidParamsException {
		Map<String, String> errors = Maps.newHashMap();

		if (StringUtils.isBlank(email.trim())) {
			errors.put("email", MessageUtil.EMAIL_ADDRESS_IS_NOT_ENTERED);
			return;
		} else if (!email.trim().matches("^\\s*[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\\s*$")) {
			errors.put("email", MessageUtil.EMAIL_IS_NOT_FORMAT);
			return;
		}
		Account account = accountRepository.findByEmail(email);
		if (account == null) {
			errors.put("email", PropertiesUtil.getMessage(MessageUtil.EMAIL_DOES_NOT_EXIST, language));

		}
		if (!errors.isEmpty()) {
			throw new VietTienInvalidParamsException(errors);
		}
		code = StringUtil.generateOtp();
		account.setOtp(code);
		Date now = new Date();
		account.setOtpExpires(now);
		accountRepository.save(account);
		EmailUtil.getInstance().sendLostPasswordEmail(messageSource, language, account.getEmail(), code, account.getFullName());
	}

	@Transactional
	public void confirmationOtpAndForgetPassWord(String language, String email, String password) throws VietTienInvalidParamsException {
		Map<String, String> errors = Maps.newHashMap();
		if (StringUtils.isBlank(email.trim())) {
			errors.put("email", MessageUtil.EMAIL_ADDRESS_IS_NOT_ENTERED);
		} else if (!email.trim().matches("^\\s*[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})\\s*$")) {
			errors.put("email", MessageUtil.EMAIL_IS_NOT_FORMAT);
		}
		Account account = accountRepository.findByEmail(email);
		if (account == null) {
			errors.put("email", MessageUtil.EMAIL_ADDRESS_IS_NOT_ENTERED);
		}

		Date now = new Date();

		if (!errors.isEmpty()) {
			throw new VietTienInvalidParamsException(errors);
		}
		String newSalt = StringUtil.generateSalt();
		account.setSalt(newSalt);
		account.setPassword(StringUtil.encryptPassword(password, newSalt));
		accountRepository.save(account);
	}

	@Transactional
	public void checkOTP(Integer languageId, String email, String otp) throws VietTienInvalidParamsException {
		Map<String, String> errors = Maps.newHashMap();
		if (StringUtils.isBlank(otp.trim())) {
			errors.put("otp", PropertiesUtil.getMessage(MessageUtil.OTP_NULL, languageId));
		}
		Account account = accountRepository.findByEmail(email);
		if (!account.getOtp().equals(otp.trim())) {
			errors.put("otp", PropertiesUtil.getMessage(MessageUtil.OTP_FAIL, languageId));
		}
		if (!errors.isEmpty()) {
			throw new VietTienInvalidParamsException(errors);
		}
	}

	@Transactional
	public void changePassWord(int language, String password, String newPassword) throws VietTienInvalidParamsException {

		Map<String, String> errors = Maps.newHashMap();
		Account account = accountRepository.findAccountByAccountId(UserContextHolder.getUser().getAccountId());
		if (StringUtils.isBlank(password.trim())) {
			errors.put("password", PropertiesUtil.getMessage(MessageUtil.PASS_NULL_01, language));
		}
		String pass = account.getPassword();
		String check = StringUtil.encryptPassword(password + account.getSalt());
		if (!pass.equals(check)) {
			errors.put("password", PropertiesUtil.getMessage(MessageUtil.PASSWORD_FALSE, language));
		}
		if (!errors.isEmpty()) {
			throw new VietTienInvalidParamsException(errors);
		}
		String newSalt = StringUtil.generateSalt();
		account.setSalt(newSalt);
		account.setPassword(StringUtil.encryptPassword(newPassword, newSalt));
		accountRepository.save(account);
	}

	@Transactional
	public AccountResponse update(AccountRequest accountRequest) throws VietTienInvalidParamsException, VietTienException {
		Account account = UserContextHolder.getUser();
		// validate file avatar path
		Map<String, String> errors = Maps.newHashMap();
		MultipartFile multipartFile = accountRequest.getImages();
		if (multipartFile != null && !multipartFile.isEmpty() && !FileUtil.isAcceptFileTypeImage(multipartFile.getOriginalFilename())) {
			errors.put("files", String.format("File's extension (%s) is invalid, only support: %s",
					FileUtil.getFileExtFromFileName(Objects.requireNonNull(multipartFile.getOriginalFilename())),
					acceptImageTypes.replace(",", ", ")));
		}
		if (!errors.isEmpty()) {
			throw new VietTienInvalidParamsException(errors);
		}

		// validate email
		if (StringUtils.isNotBlank(accountRequest.getEmail())) {
			Account oldAccount = accountRepository.findByEmail(accountRequest.getEmail());
			if (oldAccount != null && !oldAccount.getAccountId().equals(account.getAccountId())) {
				throw new VietTienException("Email đã tồn tại trên hệ thống");
			}
		}
		// save avatar files path
		Date now = new Date();
		account.setImages(accountRequest.getImages() != null ? FileUtil.saveFile(accountRequest.getImages()) : account.getImages());
		account.setFullName(StringUtils.isNotBlank(accountRequest.getFullName()) ? accountRequest.getFullName().trim() : account.getFullName());
		account.setPhone(StringUtils.isNotBlank(accountRequest.getPhone()) ? accountRequest.getPhone().trim() : account.getPhone());
		account.setDob(accountRequest.getDob() != null ? accountRequest.getDob() : account.getDob());
		account.setGender(accountRequest.getGender() != null ? accountRequest.getGender() : account.getGender());
		account.setEmail(StringUtils.isNotBlank(accountRequest.getEmail()) ? accountRequest.getEmail().trim() : account.getEmail());
		account.setProvinceId(accountRequest.getProvinceId() != null ? accountRequest.getProvinceId() : account.getProvinceId());
		account.setDistrictId(accountRequest.getDistrictId() != null ? accountRequest.getDistrictId() : account.getDistrictId());
		account.setCommuneId(accountRequest.getCommuneId() != null ? accountRequest.getCommuneId() : account.getCommuneId());
		account.setAddress(accountRequest.getAddress() != null ? accountRequest.getAddress() : account.getAddress());
		account.setUpdateBy(account.getAccountId());
		account.setUpdateDate(now);
		accountRepository.save(account);
		AccountResponse accountResponse = new AccountResponse();
		BeanUtils.copyProperties(account, accountResponse);
		accountResponse.setAvatarPath(StringUtil.buildURI(staticContext, account.getImages()));
		return accountResponse;
	}
}
