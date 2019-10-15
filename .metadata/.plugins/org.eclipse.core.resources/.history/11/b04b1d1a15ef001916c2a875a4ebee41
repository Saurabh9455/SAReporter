package com.SuperemeAppealReporter.api.io.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.io.dao.AdminDao;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;

@Component
public class AdminDaoImpl implements AdminDao {
	
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Page<UserEntity> getUserEntityPageByUserType(Pageable pageableRequest,String userType) {
		
		Page<UserEntity> userEntityPage = userRepository.getUserEntityPageByUserType(userType,pageableRequest);
	
		return userEntityPage;
	}

	@Override
	public Page<UserEntity> getActiveUserEntityPageByUserType(Pageable pageableRequest, String userType) {
		// TODO Auto-generated method stub
		Page<UserEntity> userEntityPage = userRepository.getActiveUserEntityPageByUserType(userType,pageableRequest);
		return userEntityPage;
	}

	@Override
	public Page<UserEntity> getInActiveUserEntityPageByUserType(Pageable pageableRequest, String userType) {
		// TODO Auto-generated method stub
		Page<UserEntity> userEntityPage = userRepository.getInActiveUserEntityPageByUserType(userType,pageableRequest);
		return userEntityPage;
	}

	@Override
	public Page<UserEntity> getUserEntityPageForAllStaff(List<String> staffType, Pageable pageableRequest) {
		Page<UserEntity> userEntityPage = userRepository.getUserEntityPageForAllStaff(staffType, pageableRequest);
		
		return userEntityPage;
	}


}
