package vn.compedia.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.compedia.api.dto.auth.api.ApiSignUpDTO;
import vn.compedia.api.dto.auth.api.ApiUserDTO;
import vn.compedia.api.entity.Account;
import vn.compedia.api.exception.authentication.PasswordsDontMatchException;
import vn.compedia.api.exception.authentication.UserNotFoundHttpException;
import vn.compedia.api.exception.user.UserAlreadyExistsException;
import vn.compedia.api.exception.user.UserNotFoundException;
import vn.compedia.api.repository.auth.ApiUserRepository;
import vn.compedia.api.service.authentication.AuthenticationTokenService;
import vn.compedia.api.util.DbConstant;
import vn.compedia.api.util.user.ChangePasswordRequest;
import vn.compedia.api.util.user.GridData;
import vn.compedia.api.util.user.UserContextHolder;
import vn.compedia.api.util.user.builder.PageableBuilder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"checkstyle:ParameterNumber"})
public class ApiUserService {

	private ApiUserRepository apiUserRepository;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	private RoleService roleService;
	private AuthenticationTokenService authenticationTokenService;
	private PageableBuilder pageableBuilder;

	@Value("${account.defaultImage}")
	private String defaultImage;

	@Autowired
	public ApiUserService(ApiUserRepository apiUserRepository,
						  PasswordEncoder passwordEncoder,
						  ModelMapper modelMapper,
						  RoleService roleService,
						  AuthenticationTokenService authenticationTokenService,
						  PageableBuilder pageableBuilder) {
		this.apiUserRepository = apiUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		this.authenticationTokenService = authenticationTokenService;
		this.roleService = roleService;
		this.pageableBuilder = pageableBuilder;
	}

	public Account findByEmail(String email) throws UserNotFoundException {
		return apiUserRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Account with email: " + email + " not found"));
	}

	@Transactional
	public Account register(ApiSignUpDTO apiSignUpDTO) throws UserAlreadyExistsException {
		if (!apiSignUpDTO.getPassword().equals(apiSignUpDTO.getConfirmPassword())) {
			throw new PasswordsDontMatchException();
		}

		String email = apiSignUpDTO.getPhoneNumber();

		if (apiUserRepository.findByEmailAndRoleId(email, DbConstant.ROLE_ID_USER).isPresent()) {
			throw new UserAlreadyExistsException(email);
		}

		Account account = signUpUser(apiSignUpDTO);

		return apiUserRepository.save(account);
	}

	@Transactional
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		Account account = changePasswordRequest.getAccount();

		String encodedPassword = encodePassword(changePasswordRequest.getPassword());
		account.setPassword(encodedPassword);

		apiUserRepository.save(account);
	}

	public ApiUserDTO getUserById(Long id) {
		Account existingAccount = apiUserRepository.findById(id).orElseThrow(
				() -> new UserNotFoundHttpException("Account with id: " + id + " not found", HttpStatus.NOT_FOUND)
		);

		return modelMapper.map(existingAccount, ApiUserDTO.class);
	}

	@Transactional
	public ApiUserDTO updateUserById(Long userId, ApiUserDTO apiUserDTO) {
		return updateUser(userId, apiUserDTO);
	}

	@Transactional
	public boolean deleteUser(Long id) {
		try {
			apiUserRepository.deleteById(id);
			return true;
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundHttpException("Account with id: " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}

	public ApiUserDTO getCurrentUser() {
		Account account = UserContextHolder.getUser();

		return modelMapper.map(account, ApiUserDTO.class);
	}

	public ApiUserDTO updateCurrentUser(ApiUserDTO apiUserDTO) {
		Account account = UserContextHolder.getUser();
		Long id = account.getAccountId();
		return updateUser(id, apiUserDTO);
	}

	@Transactional
	public ApiUserDTO createUser(ApiUserDTO apiUserDTO) {
		Account account = modelMapper.map(apiUserDTO, Account.class);

		// In current version password and role are default
		account.setPassword(encodePassword("testPass"));
		account.setRoleId(DbConstant.ROLE_ID_USER);

		apiUserRepository.save(account);

		return modelMapper.map(account, ApiUserDTO.class);
	}

	private ApiUserDTO updateUser(Long id, ApiUserDTO apiUserDTO) {
		Account existingAccount = apiUserRepository.findById(id).
				orElseThrow(() -> new UserNotFoundHttpException(
						"Account with id: " + id + " not found", HttpStatus.NOT_FOUND)
				);
		Account updatedAccount = modelMapper.map(apiUserDTO, Account.class);
		updatedAccount.setAccountId(id);
		updatedAccount.setPassword(existingAccount.getPassword());
		// Current version doesn't update roles
		updatedAccount.setRoleId(existingAccount.getRoleId());
		apiUserRepository.save(updatedAccount);

		return modelMapper.map(updatedAccount, ApiUserDTO.class);
	}

	private Account signUpUser(ApiSignUpDTO apiSignUpDTO) {
		Account account = new Account();
		String encodedPassword = encodePassword(apiSignUpDTO.getPassword());
		account.setPassword(encodedPassword);
		account.setRoleId(DbConstant.ROLE_ID_USER);
		//Set default settings and image

		return account;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	private List<ApiUserDTO> mapOrdersToOrderDTO(List<Account> orders) {
		return orders.stream().map(order -> {
			ApiUserDTO dto = modelMapper.map(order, ApiUserDTO.class);
			return dto;
		}).collect(Collectors.toList());
	}

	private GridData<ApiUserDTO> parsePageToGridData(Page<Account> orderPages) {
		GridData<ApiUserDTO> gridData = new GridData<>();
		List<Account> orderList = orderPages.getContent();
		long totalCount = orderPages.getTotalElements();
		gridData.setItems(mapOrdersToOrderDTO(orderList));
		gridData.setTotalCount(totalCount);
		return gridData;
	}

//	public GridData<ApiUserDTO> getDataForGrid(UsersGridFilter filter) {
//		UserSpecificationBuilder specificationBuilder = new UserSpecificationBuilder();
//
//		Pageable paginationAndSort = pageableBuilder.build(filter);
//		Optional<Specification<Account>> optionalSpec = specificationBuilder.build(filter);
//		Page<Account> orderPages = optionalSpec
//				.map(userSpecification -> apiUserRepository.findAll(userSpecification, paginationAndSort))
//				.orElseGet(() -> apiUserRepository.findAll(paginationAndSort));
//		return parsePageToGridData(orderPages);
//	}
}
