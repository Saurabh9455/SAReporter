package com.SuperemeAppealReporter.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SucessMessage;
import com.SuperemeAppealReporter.api.enums.TokenType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.VerificationTokenDao;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.service.VerificationTokenService;
import com.SuperemeAppealReporter.api.ui.model.response.ValidateResetPasswordLinkResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class VerificationTokenServiceImpl implements VerificationTokenService  {

	@Autowired
    VerificationTokenDao verificationTokenDao;
	
	@Value("${api.reset-password.token.expire-time}")
	private long resetPasswordExpirationTime;
	
	@Override
	public ValidateResetPasswordLinkResponse validateResetPasswordLinkService(String resetToken) {
		
		System.out.println("============"+resetToken);
		/**Calling dao layer**/
		VerificationTokenEntity verificationTokenEntity = verificationTokenDao.getVerificationTokenEntityByTokenTypeAndConfirmationToken(TokenType.RESET_PASSWORD.toString(),resetToken);
		
		/**If the token does not exist in database **/
		if (verificationTokenEntity == null) {
			throw new AppException(ErrorConstant.InvalidPasswordResetTokenError.ERROR_TYPE,
					ErrorConstant.InvalidPasswordResetTokenError.ERROR_CODE,
					ErrorConstant.InvalidPasswordResetTokenError.ERROR_MESSAGE);
		}
		
		/**Checking the expiry of the token**/
		long timeInMillsForCreatedDate = verificationTokenEntity.getUpdatedDate().getTime();
		long expirationTime = timeInMillsForCreatedDate + resetPasswordExpirationTime;

		Date newDate = new Date(expirationTime);
		Date currentDate = new Date();

		if (newDate.before(currentDate)) // token Expired
		{
			throw new AppException(ErrorConstant.PasswordResetTokenExpiredError.ERROR_TYPE,
					ErrorConstant.PasswordResetTokenExpiredError.ERROR_CODE,
					ErrorConstant.PasswordResetTokenExpiredError.ERROR_MESSAGE);
		}
		
		/**Returning validate password reset token response**/
		ValidateResetPasswordLinkResponse validateResetPasswordLinkResponse = new ValidateResetPasswordLinkResponse();
		validateResetPasswordLinkResponse.setMessage(SucessMessage.ValidatePasswordResetLink.VALIDATE_PASSWORD_RESET_LINK_SUCCESS);
	    validateResetPasswordLinkResponse.setUserEmail(verificationTokenEntity.getUserEntity().getEmail());
	    return validateResetPasswordLinkResponse;
	}

	@Override
	public UserEntity validateEmailVerificationLinkService(String emaiVerificationToken) {

		/** Calling dao layer **/
		VerificationTokenEntity verificationTokenEntity = verificationTokenDao
				.getVerificationTokenEntityByTokenTypeAndConfirmationToken(TokenType.EMAIL_VERIFICATION.toString(), emaiVerificationToken);

		/**If the token does not exist in database **/
		if (verificationTokenEntity == null) {
			throw new AppException(ErrorConstant.InvalidEmailVerificationTokenError.ERROR_TYPE,
					ErrorConstant.InvalidEmailVerificationTokenError.ERROR_CODE,
					ErrorConstant.InvalidEmailVerificationTokenError.ERROR_MESSAGE);
		}
		
		/**Checking the expiry of the token**/
		long timeInMillsForCreatedDate = verificationTokenEntity.getUpdatedDate().getTime();
		long expirationTime = timeInMillsForCreatedDate + resetPasswordExpirationTime;

		Date newDate = new Date(expirationTime);
		Date currentDate = new Date();

		if (newDate.before(currentDate)) // token Expired
		{
			throw new AppException(ErrorConstant.PasswordResetTokenExpiredError.ERROR_TYPE,
					ErrorConstant.PasswordResetTokenExpiredError.ERROR_CODE,
					ErrorConstant.PasswordResetTokenExpiredError.ERROR_MESSAGE);
		}
		
		return verificationTokenEntity.getUserEntity();
		
	}

	
	
}
