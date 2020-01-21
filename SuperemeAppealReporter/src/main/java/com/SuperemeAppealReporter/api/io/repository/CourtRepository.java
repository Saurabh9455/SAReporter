package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CourtBranchEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtEntity;

@Repository
public interface CourtRepository extends PagingAndSortingRepository<CourtEntity, Integer>{

	@Query(value = "SELECT * FROM COURT where is_active = 1 and court_name = ?1 and active=1 order by created_date desc",nativeQuery = true)
	Optional<CourtEntity> findByName(String courtName);

	@Query(value = "SELECT * FROM court where is_active = 1 and id = ?1 and active=1 order by created_date desc",nativeQuery = true)
	Page<CourtEntity> findActiveCourtById(String searchValue, Pageable pageableRequest);
	
	
	
	@Query(value = "SELECT * FROM court_branch where is_active = 1 order and active=1 by created_date desc ",nativeQuery = true)
	Page<CourtBranchEntity> findAllActiveCourtV2(Pageable pageableRequest);

	@Query(value = "select c.court_type from case_entity ce left join court_detail cd on ce.court_detail_entity_id=cd.id left join court c on cd.court_entity_id = c.id where ce.id = ?1",nativeQuery=true)
	List<String[]> findRecord(int caseId);
	

}
