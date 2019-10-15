package com.SuperemeAppealReporter.api.io.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;

public interface AdminDao {

	public Page<UserEntity> getUserEntityPageByUserType(Pageable pageableRequest,String userType);
	
	public Page<UserEntity> getActiveUserEntityPageByUserType(Pageable pageableRequest,String userType);
	
	public Page<UserEntity> getInActiveUserEntityPageByUserType(Pageable pageableRequest,String userType);

	public Page<UserEntity> getUserEntityPageForAllStaff(List<String> staffType, Pageable pageableRequest);

}
