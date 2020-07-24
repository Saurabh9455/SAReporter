package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.UserCaseLibraryEntity;

@Repository
public interface UserCaseLibraryRepository extends PagingAndSortingRepository<UserCaseLibraryEntity, Integer> {

	@Query(value = "select * from user_case_library  where is_active = 1 and user_entity_id = ?1 order by created_date desc",nativeQuery = true)
	public Page<UserCaseLibraryEntity> getAllEntriesForUser(long userId,Pageable pageableRequest);
	
	@Query(value = "select id from user_case_library  where is_active = 1 and user_entity_id = ?1 and case_entity_id = ?2 ",nativeQuery = true)
	public List<Integer> getAllPreviousCaseList(int user_id, int case_id);
	
	@Query(value = "from UserCaseLibraryEntity uE where uE.userEntity.email = ?1 AND uE.active = 1 AND  uE.caseEntity.docId = ?2")
	public UserCaseLibraryEntity getUserCaseLibraryEntityByUserEmailAndDocId(String userEmail,Long docId );
}
