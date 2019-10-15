package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Integer>{

	@Query(value = "select * from user where email = ?1",nativeQuery = true)
	UserEntity getUserEntityByEmail(String email);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1",nativeQuery = true)
	Page<UserEntity> getUserEntityPageByUserType(String userType, Pageable pageable);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1 and is_subscription_active=1",nativeQuery = true)
	Page<UserEntity> getActiveUserEntityPageByUserType(String userType,Pageable pageable);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1 and is_subscription_active=0",nativeQuery = true)
	Page<UserEntity> getInActiveUserEntityPageByUserType(String userType,Pageable pageable);
	
	@Query(value = "select * from user where type in ?1 and is_active = 1",nativeQuery = true)
	Page<UserEntity> getUserEntityPageForAllStaff(List<String> staffType,Pageable pageableRequest);

}