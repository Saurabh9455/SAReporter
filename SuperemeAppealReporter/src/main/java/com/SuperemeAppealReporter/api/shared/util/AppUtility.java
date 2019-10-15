package com.SuperemeAppealReporter.api.shared.util;

import java.util.Random;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;

public class AppUtility {

	public static VerificationTokenEntity generateVerificationToken(UserEntity employerEntity)
	{
		VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(employerEntity);
		return verificationTokenEntity;
	
	}
	
	public static  String generateRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
