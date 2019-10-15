package com.SuperemeAppealReporter.api.io.dao;

import com.SuperemeAppealReporter.api.enums.TokenType;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.shared.dto.VerificationTokenDto;

public interface VerificationTokenDao {

	public VerificationTokenDto getVerificationTokenDtoByTokenTypeAndConfirmationToken(TokenType tokenType, String confirmationToken); 
	public VerificationTokenEntity getVerificationTokenEntityByTokenTypeAndConfirmationToken(String tokenType, String confirmationToken); 
}
