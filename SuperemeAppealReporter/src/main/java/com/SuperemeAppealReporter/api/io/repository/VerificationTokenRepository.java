package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends PagingAndSortingRepository<VerificationTokenEntity, Integer> {

	@Query(value = "select * from verification where token_type = ?1 and confirmation_token =?2",nativeQuery = true)
	public VerificationTokenEntity findByTokenTypeAndConfirmationToken(String tokeType,String confirmationToken);
	
	 
}
