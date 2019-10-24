package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CourtBranchEntity;

@Repository
public interface CourtBranchRepository extends PagingAndSortingRepository<CourtBranchEntity, Integer> {

	@Query(value = "SELECT * FROM court_branch WHERE BRANCH_NAME IN ?1 and court_entity_id = ?2",nativeQuery = true)
	List<CourtBranchEntity> getAllBranchesByName(Set<String> courtBranchEntityList, Integer courtId);

}
