package com.SuperemeAppealReporter.api.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.ForgetPasswordBo;
import com.SuperemeAppealReporter.api.bo.ResetPasswordBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SucessMessage;
import com.SuperemeAppealReporter.api.converter.UserConverter;
import com.SuperemeAppealReporter.api.enums.TokenType;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.UserDao;
import com.SuperemeAppealReporter.api.io.entity.AddressEntity;
import com.SuperemeAppealReporter.api.io.entity.AuthenticationAndHistoryEntity;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.FirebaseUserDeviceMappingEntity;
import com.SuperemeAppealReporter.api.io.entity.PaymentEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.entity.UserCaseLibraryEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.UserSubscriptionDetailEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.io.repository.AuthenticationAndHistoryRepository;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.FirebaseUserDeviceMappingRepository;
import com.SuperemeAppealReporter.api.io.repository.UserCaseLibraryRepository;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.pojo.Mail;
import com.SuperemeAppealReporter.api.pojo.StaffMail;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.service.NotificationService;
import com.SuperemeAppealReporter.api.service.RoleService;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.service.VerificationTokenService;
import com.SuperemeAppealReporter.api.shared.dto.CityDto;
import com.SuperemeAppealReporter.api.shared.dto.CountryDto;
import com.SuperemeAppealReporter.api.shared.dto.StateDto;
import com.SuperemeAppealReporter.api.shared.dto.UserDto;
import com.SuperemeAppealReporter.api.shared.util.AppUtility;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseToMyLibraryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadProfilePictureRequest;
import com.SuperemeAppealReporter.api.ui.model.response.AddStaffResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CustomSignupResponse;
import com.SuperemeAppealReporter.api.ui.model.response.DahsboardResponse;
import com.SuperemeAppealReporter.api.ui.model.response.EmailVerificationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ForgetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.LoginHistoryResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.SearchCaseListResponse;
import com.SuperemeAppealReporter.api.ui.model.response.UserOrderResponse;
import com.SuperemeAppealReporter.api.ui.model.response.UserSignupResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private MasterService masterService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	CaseRepository caseRepository;
	
	@Autowired
	CaseService caseService;
	
	@Autowired
	SearchServiceImpl searchService;
	
	@Autowired
	UserCaseLibraryRepository userCaseLibraryRepository;
	
	@Autowired
	AuthenticationAndHistoryRepository authenticationAndHistoryRepository;
	
	@Autowired
	FirebaseUserDeviceMappingRepository firebaseUserDeviceMappingRepository;

	@Value("${api.reset-password.token.expire-time}")
	private long resetPasswordExpirationTime;
	
	@Value("${file.profile-upload-dir}")
	private String fileUploadDirectoryForProfilePicture;

	public UserSignupResponse userSignupService(UserSignupBo userSignupBo) {
		
		try
		{
		/** Checking if the user already exists **/
		String userEmail = userSignupBo.getEmail();
		UserDto userDto = userDao.getUserDtoByEmail(userEmail);
		if (userDto != null) {
			throw new AppException(ErrorConstant.UserAlreadyExistsError.ERROR_TYPE,
					ErrorConstant.UserAlreadyExistsError.ERROR_CODE,
					ErrorConstant.UserAlreadyExistsError.ERROR_MESSAGE);
		}

		/** Converting Bo to entity **/
		UserEntity userEntity = UserConverter.convertUserSignupBoToUserEntity(userSignupBo);
		System.out.println("EEEE" + userEntity.getPassword());
		RoleEntity roleEntity = null;
		if (userSignupBo.getRoleId() == 2) {

			/** Fetching role entity from role table **/
			roleEntity = roleService.findByRoleId(2);
			userEntity.setUserType(roleEntity.getName());

		} else {
			/** Fetching role entity from role table **/
			roleEntity = roleService.findByRoleId(userSignupBo.getRoleId());
			userEntity.setUserType(roleEntity.getName());

		}

		/** Creating address entity **/
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setCityEntity(masterService.getCityEntityByCityId(userSignupBo.getCityId()));
		addressEntity.setStateEntity(masterService.getStateEntityByStateId(userSignupBo.getStateId()));
		addressEntity.setCountryEntity(masterService.getCountryEntityByCountryId(userSignupBo.getCountryId()));
		addressEntity.setAddress(userSignupBo.getAddress());
		System.out.println("ZIPCODE------>" + userSignupBo.getZipCode());
		addressEntity.setZipcode(userSignupBo.getZipCode());
		userEntity.setAddressEntity(addressEntity);

		/** Generating client id **/
	/*	ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();*/
		int nextClientId = AppUtility.genClientId();
		userEntity.setClientId(nextClientId);

		/** Assigning Role to User **/
		ArrayList<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(roleEntity);
		userEntity.setRoleEntityList(roleList);

		/** Assigning verification token **/
		VerificationTokenEntity verificationTokenEntity = AppUtility.generateVerificationToken(userEntity);
		verificationTokenEntity.setTokenType(TokenType.EMAIL_VERIFICATION); // setting TokenType
		List<VerificationTokenEntity> verificationTokenEntities = new ArrayList<VerificationTokenEntity>();
		verificationTokenEntities.add(verificationTokenEntity);
		userEntity.setVerificationTokenEntity(verificationTokenEntities);

		/** Calling dao layer **/
		userDao.saveUserEntity(userEntity);

		/** Creating OnBoardingMail object **/
		Mail onBoardingMail = new Mail();
		onBoardingMail.setBelongsTo(UserType.USER);
		onBoardingMail.setTo(userEntity.getEmail());
		onBoardingMail.setSubject(AppConstant.Mail.OnBoardingMail.SUBJECT);
		Map<String, Object> onBoardingModel = new HashMap<String, Object>();
		onBoardingModel.put(AppConstant.Mail.USERNAME_KEY, userEntity.getName());
		String emailVerificationUrl = AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL + "?token="
				+ verificationTokenEntity.getConfirmationToken();
		onBoardingModel.put(AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL_KEY, emailVerificationUrl);
		onBoardingMail.setModel(onBoardingModel);
		String updatedFlag = "N";
		/** Sending OnBoaringMail **/
		try {
			notificationService.sendEmailNotification(onBoardingMail, updatedFlag);
		} catch (MessagingException | IOException ex) {
			throw new AppException(ErrorConstant.SendingEmailError.ERROR_TYPE,
					ErrorConstant.SendingEmailError.ERROR_CODE, ErrorConstant.SendingEmailError.ERROR_MESSAGE);
		}

		/** Generating and returning response from service **/
		UserSignupResponse userSignupResponse = new UserSignupResponse();
		userSignupResponse.setMessage(SucessMessage.UserSignup.USER_SIGNUP_SUCCESS);

		return userSignupResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> userSignupService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}

	}

	@Override
	public boolean checkEmailVerification(String email) {

		try
		{
		UserDto userDto = userDao.getUserDtoByEmail(email);
		return userDto.isEmailVerified();
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> checkEmailVerification()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ForgetPasswordResponse forgetPasswordService(ForgetPasswordBo forgetPasswordBo) {

		try
		{
			
		
		/** Checking if the user exists **/
		String userEmail = forgetPasswordBo.getUserEmail();
		UserEntity userEntity = userDao.getUserEntityByEmail(userEmail);

		if (userEntity == null) {
			throw new AppException(ErrorConstant.UserDoesNotExistError.ERROR_TYPE,
					ErrorConstant.UserDoesNotExistError.ERROR_CODE, ErrorConstant.UserDoesNotExistError.ERROR_MESSAGE);
		}

		List<VerificationTokenEntity> verificationTokenEntities = userEntity.getVerificationTokenEntity();

		boolean verificationTokenAlreadyExist = false;
		VerificationTokenEntity verificationTokenEntityOuter = new VerificationTokenEntity();
		for (VerificationTokenEntity verificationTokenEntity : verificationTokenEntities) {
			if (verificationTokenEntity.getTokenType().equals(TokenType.RESET_PASSWORD)) {

				long timeInMillsForCreatedDate = verificationTokenEntity.getUpdatedDate().getTime();
				long expirationTime = timeInMillsForCreatedDate + resetPasswordExpirationTime;
				Date newDate = new Date(expirationTime);
				Date currentDate = new Date();
				if (newDate.before(currentDate)) // token Expired
				{
					verificationTokenEntity.setConfirmationToken(
							AppUtility.generateVerificationToken(userEntity).getConfirmationToken());

				}
				verificationTokenAlreadyExist = true;
				verificationTokenEntityOuter = verificationTokenEntity;
				break;
			}
		}

		if (!verificationTokenAlreadyExist) {
			verificationTokenEntityOuter = AppUtility.generateVerificationToken(userEntity);
			verificationTokenEntityOuter.setTokenType(TokenType.RESET_PASSWORD);
			verificationTokenEntities.add(verificationTokenEntityOuter);

		}

		/** Creating resetPassword object **/
		Mail resetPasswordMail = new Mail();
		resetPasswordMail.setBelongsTo(UserType.USER);
		resetPasswordMail.setTo(userEntity.getEmail());
		resetPasswordMail.setSubject(AppConstant.Mail.ForgetPasswordMail.SUBJECT);
		Map<String, Object> resetPasswordModel = new HashMap<String, Object>();
		resetPasswordModel.put(AppConstant.Mail.USERNAME_KEY, userEntity.getName());
		String resetPasswordUrl = AppConstant.Mail.ForgetPasswordMail.FORGET_PASSWORD_REDIRECT_URL + "?token="
				+ verificationTokenEntityOuter.getConfirmationToken();
		resetPasswordModel.put(AppConstant.Mail.ForgetPasswordMail.FORGET_PASSWORD_REDIRECT_URL_KEY, resetPasswordUrl);
		resetPasswordMail.setModel(resetPasswordModel);
		String updatedFlag = "N";
		/** Sending OnBoaringMail **/
		try {
			notificationService.sendEmailNotification(resetPasswordMail,updatedFlag);
		} catch (MessagingException | IOException ex) {
			throw new AppException(ErrorConstant.SendingEmailError.ERROR_TYPE,
					ErrorConstant.SendingEmailError.ERROR_CODE, ErrorConstant.SendingEmailError.ERROR_MESSAGE);
		}

		/** Generating and returning response from service **/
		ForgetPasswordResponse forgetPasswordResponse = new ForgetPasswordResponse();
		forgetPasswordResponse.setMessage(SucessMessage.ForgetPassword.FORGET_PASSWORD_SUCCESS);
		return forgetPasswordResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> forgetPasswordService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
	}

	@Override
	public ResetPasswordResponse resetPasswordService(ResetPasswordBo resetPasswordBo) {

		try
		{
		UserEntity userEntity = userDao.getUserEntityByEmail(resetPasswordBo.getUserEmail());
		if (userEntity == null) {
			throw new AppException(ErrorConstant.UserDoesNotExistError.ERROR_TYPE,
					ErrorConstant.UserDoesNotExistError.ERROR_CODE, ErrorConstant.UserDoesNotExistError.ERROR_MESSAGE);
		}

		/** changing the user's password **/
		userEntity.setPassword(resetPasswordBo.getUserPassword());

		/** returning the reset password response **/
		ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
		resetPasswordResponse.setMessage(SucessMessage.ResetPasswordSuccess.RESET_PASSWORD_SUCCESS);
		return resetPasswordResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> resetPasswordService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}

	}

	/** This method is used by spring security **/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	
		UserDto userDto = userDao.getUserDtoByEmail(username);

		if (userDto == null) {
			throw new  UsernameNotFoundException(ErrorConstant.AuthenticationError.USER_NOT_FOUND_ERROR_MESSAGE);
		  
		}

		try {

			/** Fetching Roles and Creating GrandetAuthorities List **/
			Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			userDto.getRoleEntityList().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			});
			System.out.println("EMAIL : "+userDto.getEmail());
			System.out.println("PASSWORD : "+userDto.getPassword());
			return new User(userDto.getEmail(), userDto.getPassword(), authorities);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
	

	}

	@Override
	public EmailVerificationResponse verifyEmailService(String emailVerificationToken) {

		try
		{
		/** Calling verification token service **/
		UserEntity userEntity = verificationTokenService.validateEmailVerificationLinkService(emailVerificationToken);

		/** Setting email verifid flag as true **/
		userEntity.setEmailVerified(true);

		/** Returning verify email service response **/
		EmailVerificationResponse emailVerificationResponse = new EmailVerificationResponse();
		emailVerificationResponse.setMessage(SucessMessage.EmailVerify.EMAIL_VERIFY_SUCCESS);
		return emailVerificationResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> verifyEmailService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}

	}

	@Override
	public CustomSignupResponse customUserSignupService(UserSignupBo userSignupBo) {

		try
		{
		
		/** Checking if the user already exists **/
		String userEmail = userSignupBo.getEmail();
		UserDto userDto = userDao.getUserDtoByEmail(userEmail);
		if (userDto != null) {
			throw new AppException(ErrorConstant.UserAlreadyExistsError.ERROR_TYPE,
					ErrorConstant.UserAlreadyExistsError.ERROR_CODE,
					ErrorConstant.UserAlreadyExistsError.ERROR_MESSAGE);
		}

		/** Converting Bo to entity **/
		UserEntity userEntity = UserConverter.convertUserSignupBoToUserEntity(userSignupBo);

		/** Generating the default password **/
		String defaultPassword = userSignupBo.getName().split(" ")[0].toLowerCase() + "_"
				+ AppUtility.generateRandomString(4);
		userEntity.setPassword(defaultPassword);

		RoleEntity roleEntity = null;
		if (userSignupBo.getRoleId() == 2) {

			/** Fetching role entity from role table **/
			roleEntity = roleService.findByRoleId(2);
			userEntity.setUserType(roleEntity.getName());

		} else {
			/** Fetching role entity from role table **/
			roleEntity = roleService.findByRoleId(userSignupBo.getRoleId());
			userEntity.setUserType(roleEntity.getName());

		}

		/** Creating address entity **/
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setCityEntity(masterService.getCityEntityByCityId(userSignupBo.getCityId()));
		addressEntity.setStateEntity(masterService.getStateEntityByStateId(userSignupBo.getStateId()));
		addressEntity.setCountryEntity(masterService.getCountryEntityByCountryId(userSignupBo.getCountryId()));
		addressEntity.setZipcode(userSignupBo.getZipCode());
		userEntity.setAddressEntity(addressEntity);

		/** Generating client id **/
		/*ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();*/
		int nextClientId = AppUtility.genClientId();
		userEntity.setClientId(nextClientId);

		/** Assigning Role to User **/
		ArrayList<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(roleEntity);
		userEntity.setRoleEntityList(roleList);

		/** Assigning verification token **/
		VerificationTokenEntity verificationTokenEntity = AppUtility.generateVerificationToken(userEntity);
		verificationTokenEntity.setTokenType(TokenType.EMAIL_VERIFICATION); // setting TokenType
		List<VerificationTokenEntity> verificationTokenEntities = new ArrayList<VerificationTokenEntity>();
		verificationTokenEntities.add(verificationTokenEntity);
		userEntity.setVerificationTokenEntity(verificationTokenEntities);

		/** Calling dao layer **/
		userDao.saveUserEntity(userEntity);

		/** Creating OnBoardingMail object **/
		Mail onBoardingMail = new Mail();
		onBoardingMail.setBelongsTo(UserType.USER);
		onBoardingMail.setTo(userEntity.getEmail());
		onBoardingMail.setSubject(AppConstant.Mail.OnBoardingMail.CUSTOM_SUBJECT);
		Map<String, Object> onBoardingModel = new HashMap<String, Object>();
		onBoardingModel.put(AppConstant.Mail.EMAIL_KEY, userEntity.getEmail());
		onBoardingModel.put(AppConstant.Mail.PASSWORD_KEY, userEntity.getPassword());
		onBoardingModel.put(AppConstant.Mail.USERNAME_KEY, userEntity.getName());
		String emailVerificationUrl = AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL + "?token="
				+ verificationTokenEntity.getConfirmationToken();
		onBoardingModel.put(AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL_KEY, emailVerificationUrl);
		onBoardingMail.setModel(onBoardingModel);
		String updatedFlag = "N";
		/** Sending OnBoaringMail **/
		try {
			notificationService.sendEmailNotification(onBoardingMail,updatedFlag);
		} catch (MessagingException | IOException ex) {
			throw new AppException(ErrorConstant.SendingEmailError.ERROR_TYPE,
					ErrorConstant.SendingEmailError.ERROR_CODE, ErrorConstant.SendingEmailError.ERROR_MESSAGE);
		}

		/** Generating and returning response from service **/
		CustomSignupResponse customSignupResponse = new CustomSignupResponse();
		customSignupResponse.setMessage(SucessMessage.CustomUserSignup.CUSTOM_USER_SIGNUP_SUCCESS);
		customSignupResponse.setUserId(userEmail);
		customSignupResponse.setUserPassword(defaultPassword);
		return customSignupResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> customUserSignupService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
	}

	@Override
	public AddStaffResponse addStaff(AddStaffBo addStaffBo) {

		try
		{
		/** Checking if the user already exists **/
		String userEmail = addStaffBo.getEmail();
		UserDto userDto = userDao.getUserDtoByEmail(userEmail);
		if (userDto != null) {
			throw new AppException(ErrorConstant.UserAlreadyExistsError.ERROR_TYPE,
					ErrorConstant.UserAlreadyExistsError.ERROR_CODE,
					ErrorConstant.UserAlreadyExistsError.ERROR_MESSAGE);
		}

		/** Converting Bo to entity **/
		UserEntity userEntity = UserConverter.convertAddStaffBoToUserEntity(addStaffBo);

		/** Generating the default password **/
		String defaultPassword = addStaffBo.getName().split(" ")[0].toLowerCase() + "_"
				+ AppUtility.generateRandomString(4);
		userEntity.setPassword(defaultPassword);

		RoleEntity roleEntity = null;

		/** Fetching role entity from role table **/
		roleEntity = roleService.findByRoleId(addStaffBo.getRoleId());
		userEntity.setUserType(roleEntity.getName());
		/** Creating address entity **/
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setCityEntity(masterService.getCityEntityByCityId(addStaffBo.getCityId()));
		addressEntity.setStateEntity(masterService.getStateEntityByStateId(addStaffBo.getStateId()));
		addressEntity.setCountryEntity(masterService.getCountryEntityByCountryId(addStaffBo.getCountryId()));
		addressEntity.setZipcode(addStaffBo.getZipCode());
		userEntity.setAddressEntity(addressEntity);

		/** Generating client id **/
		/*ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();*/
		int nextClientId = AppUtility.genClientId();
		userEntity.setClientId(nextClientId);

		/** Assigning Role to User **/
		ArrayList<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(roleEntity);
		userEntity.setRoleEntityList(roleList);

		/** Assigning verification token **/
		VerificationTokenEntity verificationTokenEntity = AppUtility.generateVerificationToken(userEntity);
		verificationTokenEntity.setTokenType(TokenType.EMAIL_VERIFICATION); // setting TokenType
		List<VerificationTokenEntity> verificationTokenEntities = new ArrayList<VerificationTokenEntity>();
		verificationTokenEntities.add(verificationTokenEntity);
		userEntity.setVerificationTokenEntity(verificationTokenEntities);

		/** Calling dao layer **/
		userDao.saveUserEntity(userEntity);

		/** Creating OnBoardingMail object **/
		StaffMail onBoardingMail = new StaffMail();
		onBoardingMail.setBelongsTo(roleEntity.getName());
		onBoardingMail.setTo(userEntity.getEmail());
		onBoardingMail.setSubject(AppConstant.Mail.OnBoardingMail.CUSTOM_SUBJECT);
		Map<String, Object> onBoardingModel = new HashMap<String, Object>();
		onBoardingModel.put(AppConstant.Mail.EMAIL_KEY, userEntity.getEmail());
		onBoardingModel.put(AppConstant.Mail.PASSWORD_KEY, userEntity.getPassword());
		onBoardingModel.put(AppConstant.Mail.USERNAME_KEY, userEntity.getName());
		onBoardingModel.put(AppConstant.Mail.ROLE_ASSIGNED, roleEntity.getName());
		String emailVerificationUrl = AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL + "?token="
				+ verificationTokenEntity.getConfirmationToken();
		onBoardingModel.put(AppConstant.Mail.OnBoardingMail.EMAIL_VERIFICATION_REDIRECT_URL_KEY, emailVerificationUrl);
		onBoardingMail.setModel(onBoardingModel);

		String updateStaffFlag = "N";
		/** Sending OnBoaringMail **/
		try {
			notificationService.sendStaffEmailNotification(onBoardingMail,updateStaffFlag);
		} catch (MessagingException ex) {
			throw new AppException(ErrorConstant.SendingEmailError.ERROR_TYPE,
					ErrorConstant.SendingEmailError.ERROR_CODE, ErrorConstant.SendingEmailError.ERROR_MESSAGE);
		}

		/** Generating and returning response from service **/
		AddStaffResponse customSignupResponse = new AddStaffResponse();
		customSignupResponse.setMessage(SucessMessage.StaffMessage.STAFF_CREATED);
		customSignupResponse.setUserId(userEmail);
		customSignupResponse.setUserPassword(defaultPassword);
		return customSignupResponse;
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in UserServiceImpl --> addStaff()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}

	}

	@Override
	public UserEntity findByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.getUserEntityByEmail(email);
	}

	@Override
	public DahsboardResponse giveDashboardResponseService(String emailId) {
		
		DahsboardResponse dashboardResponse = null;
		try
		{
		UserEntity userEntity = findByEmail(emailId);
		
		String profilePicturePath = userEntity.getProfilePicturePath();
		
	    if(profilePicturePath==null){
	    	profilePicturePath =fileUploadDirectoryForProfilePicture+"/default.jpeg";
	    }
		
		AddressEntity addressEntity = userEntity.getAddressEntity();
		CityEntity cityEntity = addressEntity.getCityEntity();
		CountryEntity countryEntity = addressEntity.getCountryEntity();
		StateEntity stateEntity = addressEntity.getStateEntity();
		
		
		dashboardResponse = new DahsboardResponse();
		dashboardResponse.setName(userEntity.getName());
		dashboardResponse.setDesgination(userEntity.getDesgination());
		dashboardResponse.setEmail(userEntity.getEmail());
		dashboardResponse.setMobile(userEntity.getMobile());
		dashboardResponse.setSubscriptionActive(userEntity.isSubscriptionActive());
		dashboardResponse.setPassword(userEntity.getPassword());
		dashboardResponse.setClientId(userEntity.getClientId());
		dashboardResponse.setZipCode(addressEntity.getZipcode());
		dashboardResponse.setAddress(addressEntity.getAddress());
		StateDto stateDto = new StateDto();
		stateDto.setId(stateEntity.getId());
		stateDto.setLabel(stateEntity.getName());
		stateDto.setValue(stateEntity.getName());
		
		CountryDto countryDto = new CountryDto();
		countryDto.setId(countryEntity.getId());
		countryDto.setLabel(countryEntity.getName());
		countryDto.setValue(countryEntity.getName());
		
		CityDto cityDto = new CityDto();
		cityDto.setId(cityEntity.getId());
		cityDto.setLabel(cityEntity.getName());
		cityDto.setValue(cityEntity.getName());
		
		dashboardResponse.setStateDto(stateDto);
		dashboardResponse.setCityDto(cityDto);
		dashboardResponse.setCountryDto(countryDto);

		List<UserSubscriptionDetailEntity> userSubscriptionDetailEntityList = userEntity.getUserSubscriptionDetailEntityList();
		
		List<UserOrderResponse> userOrderResponseList = new ArrayList<UserOrderResponse>();
		
		for(UserSubscriptionDetailEntity entity : userSubscriptionDetailEntityList)
		{
			PaymentEntity paymentEntity = entity.getPaymentEntity();
			double paymentAmount = paymentEntity.getAmount();
			String paymentMode = paymentEntity.getPaymentMode().toString();
			String paymentStatus = paymentEntity.getPaymentStatus().toString();
			Date paymentDate = paymentEntity.getCreatedDate();
			UserOrderResponse userOrderResponse = new UserOrderResponse();
			userOrderResponse.setPaymentAmount(paymentAmount);
			userOrderResponse.setPaymentDate(paymentDate);
			userOrderResponse.setPaymentStatus(paymentStatus);
			userOrderResponse.setPlanActive(entity.getIs_plan_active());
			userOrderResponse.setPlanEndDate(entity.getEndDate());
			userOrderResponse.setPlanStartDate(entity.getStartDate());
			userOrderResponse.setTransactionId(paymentEntity.getTransaction_id());
			userOrderResponse.setPaymentMode(paymentMode);
			userOrderResponse.setPaymentId(paymentEntity.getPayment_id());
			userOrderResponse.setPlanType(entity.getSubscriptionPlanEntity().getSubscriptionName());
			if(entity.getStartDate().after(new Date()))
			{
				userOrderResponse.setFuturePlan(true);
			}
			else
			{
				userOrderResponse.setFuturePlan(false);
			}
			userOrderResponseList.add(userOrderResponse);
			
		}
		
		dashboardResponse.setUserOrderList(userOrderResponseList);
		
	
		
		byte[] fileContent = FileUtils.readFileToByteArray(new File(profilePicturePath));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		
		dashboardResponse.setProfilePictureBase64EncodedString(encodedString);
		
		
		
		
		}
				
				catch(AppException appException)
				{
					throw appException;
				}
				catch(Exception ex)
				{
					String errorMessage = "Error in UserServiceImpl --> giveDashboardResponseService()";
					AppException appException = new AppException("Type : " + ex.getClass()
					+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
							ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
					throw appException;
					
				}
		return dashboardResponse;
	}

	@Override
	public CommonMessageResponse uploadProfilePicture(UploadProfilePictureRequest uploadProfilePictureRequest) {
		
		CommonMessageResponse commonMessageResponse = null;
		try {
			
			String originalProfilePicturePath = saveProfilePicture(uploadProfilePictureRequest.getFile(), uploadProfilePictureRequest.getUserEmail());
			UserEntity userEntity = this.userRepository.getUserEntityByEmail(uploadProfilePictureRequest.getUserEmail());
			if (userEntity == null)
				throw new AppException("Profile Picture Upload ERROR", "222",
						"User Record not found");
			
			userEntity.setProfilePicturePath(originalProfilePicturePath);
			commonMessageResponse = new CommonMessageResponse("PDF File Uploaded Successfully");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in UserServiceImpl --> uploadProfilePicture()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonMessageResponse;
	}

	private String saveProfilePicture(MultipartFile file, String userEmail) {
		try {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			String name = file.getOriginalFilename();
			System.out.println("------ORIGINAL FILE NAME----" + name);
			String extension = null;
			int lastIndexOf = name.lastIndexOf(".");
			if (lastIndexOf == -1) {
				extension = "";
			} else {
				extension = name.substring(lastIndexOf);
			}
			String fileName = "profile_" + userEmail + extension;
			System.out.println("---------EXTENSION--------" + extension);
			File newFile = new File(this.fileUploadDirectoryForProfilePicture + "/" + fileName);
			inputStream = file.getInputStream();
			if (!newFile.exists())
				newFile.createNewFile();
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1)
				outputStream.write(bytes, 0, read);
			outputStream.close();
			String absolutePath = newFile.getAbsolutePath();
			return absolutePath;
		} catch (IOException e) {
			e.printStackTrace();
			AppException appException = new AppException("File Upload ERROR", "798", "Unable to upload Profile Picture.");
			throw appException;
		}
	}

	@Override
	public CommonMessageResponse addCaseToMyLibraryService(AddCaseToMyLibraryRequest addCaseToMyLibraryRequest) {
		
		CommonMessageResponse commonMessageResponse = null;
		try
		{
		String docId = addCaseToMyLibraryRequest.getDocId();
		
		CaseEntity caseEntity = caseRepository.findByDocId(Long.parseLong(docId));
		
		UserEntity userEntity = userRepository.getUserEntityByEmail( SecurityContextHolder.getContext().getAuthentication().getName());
	
		/**checking whether the case already exists to the user library**/
		
		
		List<Integer> previousList = userCaseLibraryRepository.getAllPreviousCaseList(userEntity.getId(), caseEntity.getId());
		
		if(previousList!=null && previousList.size()>0){
			throw new AppException(ErrorConstant.AddToMyLibraryError.ERROR_TYPE,
					ErrorConstant.AddToMyLibraryError.ERROR_CODE,
					ErrorConstant.AddToMyLibraryError.ERROR_MESSAGE);
		}
		
		UserCaseLibraryEntity userCaseLibraryEntity = new UserCaseLibraryEntity();
		userCaseLibraryEntity.setUserEntity(userEntity);
		userCaseLibraryEntity.setCaseEntity(caseEntity);
		userCaseLibraryRepository.save(userCaseLibraryEntity);
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg("Case added to My Library successfully");
	} catch (AppException appException) {
		throw appException;
	} catch (Exception ex) {
		ex.printStackTrace();
		String errorMessage = "Error in UserServiceImpl --> addCaseToMyLibraryService()";
		AppException appException = new AppException(
				"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
				"Error Description  :  : " + errorMessage);
		throw appException;
	}
	return commonMessageResponse;
	}

	@Override
	public CommonPaginationResponse getMyLibraryCaseList(int pageNumber) {
	
		CommonPaginationResponse commonPaginationResponse = null;
		try
		{
		UserEntity userEntity = userRepository.getUserEntityByEmail( SecurityContextHolder.getContext().getAuthentication().getName());
		int perPage = 8;
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPage);
		
		Page<UserCaseLibraryEntity> userCaseLibraryEntityPage = userCaseLibraryRepository.getAllEntriesForUser(userEntity.getId(),pageableRequest);
		
		List<UserCaseLibraryEntity> userCaseLibraryEntities = userCaseLibraryEntityPage.getContent();
		List<String> caseIdList = new ArrayList<String>();
		
		for(UserCaseLibraryEntity userCaseLibraryEntity : userCaseLibraryEntities){
			
			caseIdList.add(userCaseLibraryEntity.getCaseEntity().getId()+"");
		}
		
		Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

		searchCaseListResponseSet = searchService.prepareCaseRepresentation(caseIdList,pageNumber+1);
		commonPaginationResponse = new CommonPaginationResponse();

		commonPaginationResponse.setOjectList(searchCaseListResponseSet);
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userCaseLibraryEntityPage.getTotalPages());
		
		
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in UserServiceImpl --> getMyLibraryCaseList()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonPaginationResponse;
		}

	@Override
	
	public void logUserActivity(String token, String userEmail,boolean isLoginPerformed,int userType) {
		
		
		/**For Login**/
		if(isLoginPerformed){
			
			UserEntity userEntity = userRepository.getUserEntityByEmail(userEmail);
			
		/*	if (userType == 3) {   //we have to disable all previous logins for only those which has role "USER"
*/
				List<AuthenticationAndHistoryEntity> currentActiveLoginList = authenticationAndHistoryRepository
						.getAllActiveLoginDetails(userEntity.getId());

				for (AuthenticationAndHistoryEntity entity : currentActiveLoginList) {

					entity.setActive(false);
				
					 entity.setLogoutTimestamp(new Date());
						
					 long diffInMillies = Math.abs(entity.getLogoutTimestamp().getTime() - entity.getLoginTimestamp().getTime());
					 long minutesActive = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
					 entity.setActiveDurationInMinutes(minutesActive);
				}
			
			
			AuthenticationAndHistoryEntity newActiveLogin = new AuthenticationAndHistoryEntity();
			newActiveLogin.setActive(true);
			newActiveLogin.setJwtToken(token);
			newActiveLogin.setLoginTimestamp(new Date());
			newActiveLogin.setUserEntity(userEntity);
			
			
			authenticationAndHistoryRepository.save(newActiveLogin);
		}
		
		/**For Logout**/
		else{
			
			UserEntity userEntity = userRepository.getUserEntityByEmail(userEmail);
			List<AuthenticationAndHistoryEntity> currentActiveLoginList =  authenticationAndHistoryRepository.getAllActiveLoginDetails(userEntity.getId());
		
			for(AuthenticationAndHistoryEntity entity : currentActiveLoginList ){
				
				 entity.setActive(false);
				 entity.setLogoutTimestamp(new Date());
				
				 long diffInMillies = Math.abs(entity.getLogoutTimestamp().getTime() - entity.getLoginTimestamp().getTime());
				 long minutesActive = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
				 entity.setActiveDurationInMinutes(minutesActive);
				 
				 
				  
			}
			
		
		}
		
	}

	@Override
	public AuthenticationAndHistoryEntity getAuthenticationAndHistoryEntityByToken(String token) {
	
	return authenticationAndHistoryRepository.getAuthenticationAndHistoryEntityByToken(token);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CommonMessageResponse logOut(String token,String email,String clientHeader) {
	
		CommonMessageResponse commonPaginationResponse = null;
		try
		{
	
		if(!"MOBILE_APP".equals(clientHeader)){
			logUserActivity(token,email,false,1); //here the the 4th parameter i.e user type does not matter beacuse its logout
		}
		else {
			FirebaseUserDeviceMappingEntity firebaseEntity =firebaseUserDeviceMappingRepository.getEntityByUserEmail(email);
			firebaseEntity.setUserLoggedIn(false);
		}
		
			commonPaginationResponse = new CommonMessageResponse();
			commonPaginationResponse.setMsg("Logged Out Successfully");
		} catch (AppException appException) {
			throw appException;
		} catch (Exception ex) {
			ex.printStackTrace();
			String errorMessage = "Error in UserServiceImpl --> logOut()";
			AppException appException = new AppException(
					"Type : " + ex.getClass() + ", Cause : " + ex.getCause() + ", Message : " + ex.getMessage(), "500",
					"Error Description  :  : " + errorMessage);
			throw appException;
		}
		return commonPaginationResponse;
		}

	@Override
	public CommonPaginationResponse getLoginHistory(String email,int pageNumber,int perPage) {
	
		CommonPaginationResponse commonPaginationResponse = null;
		try
		{
		if (pageNumber > 0)
			pageNumber = pageNumber - 1                                 ;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPage);
		Page<AuthenticationAndHistoryEntity> auPage = null;
		
		UserEntity userEntity = userRepository.getUserEntityByEmail(email);
		auPage = authenticationAndHistoryRepository.getAllLoginDetailsPage(userEntity.getId(),pageableRequest);
		
		List<AuthenticationAndHistoryEntity> entityList = auPage.getContent();
		
		List<LoginHistoryResponse> loginHistoryResponseList = new ArrayList<LoginHistoryResponse>();
		
		for(AuthenticationAndHistoryEntity entity : entityList){
			
			LoginHistoryResponse loginHistoryResponse = new LoginHistoryResponse();
			BeanUtils.copyProperties(entity, loginHistoryResponse);
			loginHistoryResponseList.add(loginHistoryResponse);
		}
		
		commonPaginationResponse  = new CommonPaginationResponse();
		commonPaginationResponse.setOjectList(loginHistoryResponseList);
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(auPage.getTotalPages());
	}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in UserServiceImpl --> getTransactionHistorygetTransactionHistory()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		return commonPaginationResponse;
		
	}

	@Override
	public String getEmailByClientId(String clientId) {
	
		String email = null;
	UserEntity userEntity = userRepository.getUserByStaffId(Integer.parseInt(clientId));
		
	if(userEntity!=null){
		
		email =  userEntity.getEmail();
	
	}
	return email;
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CommonMessageResponse deleteCaseForMyLibrary(String userEmail,String docId) {
		
		CommonMessageResponse commonMessageResponse = null;
		try
		{
		
		UserCaseLibraryEntity userCaseLibraryEntity = userCaseLibraryRepository.getUserCaseLibraryEntityByUserEmailAndDocId(userEmail,Long.parseLong(docId));
		
		userCaseLibraryEntity.setActive(false);
		
		
		commonMessageResponse  = new CommonMessageResponse();
		commonMessageResponse.setMsg("Case deleted form My Library");
		}
		catch(Exception ex){
			ex.printStackTrace();
			String errorMessage = "Error in UserServiceImpl --> deleteCaseForMyLibrary()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		return commonMessageResponse;
	}
	
	
	}
	
	
	
	

		
		
	


