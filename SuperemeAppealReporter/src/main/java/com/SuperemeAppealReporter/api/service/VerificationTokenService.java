package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.ui.model.response.ValidateResetPasswordLinkResponse;

public interface VerificationTokenService {

	public ValidateResetPasswordLinkResponse validateResetPasswordLinkService(String resetToken);
	
	public UserEntity validateEmailVerificationLinkService(String emailVerificationToken);
	
}
