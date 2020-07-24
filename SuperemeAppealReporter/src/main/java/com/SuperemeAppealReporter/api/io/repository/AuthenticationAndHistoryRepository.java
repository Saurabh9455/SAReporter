package com.SuperemeAppealReporter.api.io.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.AuthenticationAndHistoryEntity;

@Repository
public interface AuthenticationAndHistoryRepository extends PagingAndSortingRepository<AuthenticationAndHistoryEntity, Integer> {

	@Query(value = "select * from authentication_and_hisotory_entity where user_entity_id = ?1 and is_active = 1",nativeQuery = true)
	public List<AuthenticationAndHistoryEntity> getAllActiveLoginDetails(Integer userId);
	
	
	@Query(value = "select * from authentication_and_hisotory_entity where jwt_token = ?1 and is_active = 1",nativeQuery = true)
	public AuthenticationAndHistoryEntity getAuthenticationAndHistoryEntityByToken(String token);
	
	@Query(value = "select * from authentication_and_hisotory_entity where user_entity_id = ?1 order by login_time desc",nativeQuery = true)
	public Page<AuthenticationAndHistoryEntity> getAllLoginDetailsPage(Integer userId,Pageable pageableRequest);
	
	
	
}
