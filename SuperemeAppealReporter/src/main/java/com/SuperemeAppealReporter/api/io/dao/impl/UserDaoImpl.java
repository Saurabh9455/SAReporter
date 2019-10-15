package com.SuperemeAppealReporter.api.io.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.converter.UserConverter;
import com.SuperemeAppealReporter.api.io.dao.UserDao;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.shared.dto.UserDto;

@Component
public class UserDaoImpl implements UserDao {

	
	@Autowired
	private UserRepository userRepository;
	
	/*
	 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	 */
	
	@Override
	public UserDto getUserDtoByEmail(String email) {
		
		UserEntity userEntity = userRepository.getUserEntityByEmail(email);
		if(userEntity==null)
		{
			return null;
		}
		
		/** Converting Entity to Dto **/
		UserDto userDto = UserConverter.convertUserEntityToUserDto(userEntity);
		return userDto;
	}


	@Override
	public void saveUserEntity(UserEntity userEntity) {

//		userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
	//	userEntity.setPassword(userEntity.getPassword());
		System.out.println("beforeSaving----->"+userEntity.getPassword());
		userRepository.save(userEntity);
		
	}


	@Override
	public UserEntity getUserEntityByEmail(String email) {
		return userRepository.getUserEntityByEmail(email);
	}

}
