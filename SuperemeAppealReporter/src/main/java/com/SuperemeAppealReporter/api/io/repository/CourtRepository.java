package com.SuperemeAppealReporter.api.io.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CourtEntity;

@Repository
public interface CourtRepository extends PagingAndSortingRepository<CourtEntity, Integer>{

	@Query(value = "SELECT * FROM COURT where active = 1",nativeQuery = true)
	Optional<CourtEntity> findByName(String courtName);

}
