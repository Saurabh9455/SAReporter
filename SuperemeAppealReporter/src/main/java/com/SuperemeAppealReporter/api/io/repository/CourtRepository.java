package com.SuperemeAppealReporter.api.io.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CourtEntity;

@Repository
public interface CourtRepository extends PagingAndSortingRepository<CourtEntity, Integer>{

	@Query(value = "SELECT * FROM COURT where is_active = 1 and court_name = ?1",nativeQuery = true)
	Optional<CourtEntity> findByName(String courtName);

	@Query(value = "SELECT * FROM court where is_active = 1 and id = ?1",nativeQuery = true)
	Page<CourtEntity> findActiveCourtById(int courtId, Pageable pageableRequest);
	
	@Query(value = "SELECT * FROM court where is_active = 1 ",nativeQuery = true)
	Page<CourtEntity> findAllActiveCourt(Pageable pageableRequest);

}
