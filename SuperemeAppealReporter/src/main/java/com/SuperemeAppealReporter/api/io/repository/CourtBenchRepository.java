package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CourtBenchEntity;

@Repository
public interface CourtBenchRepository extends PagingAndSortingRepository<CourtBenchEntity, Integer> {

}
