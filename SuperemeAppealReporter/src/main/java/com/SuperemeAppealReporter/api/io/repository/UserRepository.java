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

	@Query(value = "select * from user where email = ?1 order by created_date desc",nativeQuery = true)
	UserEntity getUserEntityByEmail(String email);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1 order by created_date desc",nativeQuery = true)
	Page<UserEntity> getUserEntityPageByUserType(String userType, Pageable pageable);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1 and is_subscription_active=1 order by created_date desc",nativeQuery = true)
	Page<UserEntity> getActiveUserEntityPageByUserType(String userType,Pageable pageable);
	
	@Query(value = "select * from user where type = ?1 and is_active = 1 and is_subscription_active=0 order by created_date desc",nativeQuery = true)
	Page<UserEntity> getInActiveUserEntityPageByUserType(String userType,Pageable pageable);
	
	@Query(value = "select * from user where type in ?1 and is_active = 1 order by created_date desc",nativeQuery = true)
	Page<UserEntity> getUserEntityPageForAllStaff(List<String> staffType,Pageable pageableRequest);

	@Query(value = "select * from user where type = ?1 and is_active = 1 and (name like %?2% or client_id like %?2%) and is_subscription_active in ?3 ",nativeQuery = true)
	Page<UserEntity> getUserEntityPageByUserTypeAndSubscriptionTypeAndByClientNameOrId( String userType, String clientNameOrId,
			List<Integer> subsriptionTypeList,Pageable pageableRequest);

	@Query(value = "select * from user where is_active = 1 and (name like %?1% or client_id like %?1%) and type in ?2  ",nativeQuery = true)
	Page<UserEntity> getUserEntityPageByUserTypeAndSubscriptionTypeAndByClientNameOrId(String staffNameOrId,
			List<String> userTypeList, Pageable pageableRequest);

}
