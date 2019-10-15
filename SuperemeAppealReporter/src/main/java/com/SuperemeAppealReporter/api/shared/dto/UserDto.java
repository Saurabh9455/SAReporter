package com.SuperemeAppealReporter.api.shared.dto;

import java.util.List;

import com.SuperemeAppealReporter.api.io.entity.AddressEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

	private String name;
	private String email;
	private int clientId;
	private String password;
	private String desgination;
	private String mobile;
	private String userType;
	private boolean isEmailVerified;
	private AddressEntity addressEntity;
    private List<RoleEntity> roleEntityList; 
	private List<VerificationTokenEntity> verificationTokenEntity;
	private boolean isSubscriptionActive = false;
    
}
