package com.SuperemeAppealReporter.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.SuperemeAppealReporter.api.io.entity.ClientIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.pojo.Mail;
import com.SuperemeAppealReporter.api.pojo.StaffMail;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.service.NotificationService;
import com.SuperemeAppealReporter.api.service.RoleService;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.service.VerificationTokenService;
import com.SuperemeAppealReporter.api.shared.dto.UserDto;
import com.SuperemeAppealReporter.api.shared.util.AppUtility;
import com.SuperemeAppealReporter.api.ui.model.response.AddStaffResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CustomSignupResponse;
import com.SuperemeAppealReporter.api.ui.model.response.EmailVerificationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ForgetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResetPasswordResponse;
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

	@Value("${api.reset-password.token.expire-time}")
	private long resetPasswordExpirationTime;

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
		System.out.println("ZIPCODE------>" + userSignupBo.getZipCode());
		addressEntity.setZipcode(userSignupBo.getZipCode());
		userEntity.setAddressEntity(addressEntity);

		/** Generating client id **/
		ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();
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
			UsernameNotFoundException ex = new  UsernameNotFoundException(ErrorConstant.AuthenticationError.USER_NOT_FOUND_ERROR_MESSAGE);
		    ex.addSuppressed(new AppException("h","1","2"));
		    throw ex;
		}

		try {

			/** Fetching Roles and Creating GrandetAuthorities List **/
			Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			userDto.getRoleEntityList().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
			});
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
		ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();
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
		ClientIdGenerator clientIdGenerator = new ClientIdGenerator();
		masterService.save(clientIdGenerator);
		int nextClientId = masterService.giveNextClientId();
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
			String errorMessage = "Error in UserServiceImpl --> addStaff()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}

	}

}
