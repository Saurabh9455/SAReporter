package com.SuperemeAppealReporter.api.io.dao;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.shared.dto.UserDto;

public interface UserDao {

	
	public UserDto getUserDtoByEmail(String email);
	
	public UserEntity getUserEntityByEmail(String email);
	
	public void saveUserEntity(UserEntity userEntity);
	
	
	
}
