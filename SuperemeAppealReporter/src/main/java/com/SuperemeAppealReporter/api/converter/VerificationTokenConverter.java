package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;
import com.SuperemeAppealReporter.api.shared.dto.VerificationTokenDto;

public class VerificationTokenConverter {

	public static VerificationTokenDto convertVerificationTokenEntityToVerificationTokenDto(VerificationTokenEntity verificationTokenEntity)
	{
		
	   VerificationTokenDto verificationTokenDto = new VerificationTokenDto();
       BeanUtils.copyProperties(verificationTokenEntity, verificationTokenDto);
       return verificationTokenDto;
	}
	
}
