package com.SuperemeAppealReporter.api.io.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.converter.VerificationTokenConverter;
import com.SuperemeAppealReporter.api.enums.TokenType;
import com.SuperemeAppealReporter.api.io.dao.VerificationTokenDao;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.io.repository.VerificationTokenRepository;
import com.SuperemeAppealReporter.api.shared.dto.VerificationTokenDto;

@Component
public class VerifiationTokenDaoImpl implements VerificationTokenDao {

	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	
	
	@Override
	public VerificationTokenDto getVerificationTokenDtoByTokenTypeAndConfirmationToken(TokenType tokenType, String confirmationToken) {
	
		VerificationTokenEntity verificationTokenEntity = verificationTokenRepository.findByTokenTypeAndConfirmationToken(tokenType.toString(), confirmationToken);
		
		if(verificationTokenEntity==null)
		{
			return null;
		}
		
		VerificationTokenDto verificationTokenDto = VerificationTokenConverter.convertVerificationTokenEntityToVerificationTokenDto(verificationTokenEntity);
		return verificationTokenDto;
	}


	@Override
	public VerificationTokenEntity getVerificationTokenEntityByTokenTypeAndConfirmationToken(String tokenType,
			String confirmationToken) {
	
		   return verificationTokenRepository.findByTokenTypeAndConfirmationToken(tokenType, confirmationToken);
	}

	
}
