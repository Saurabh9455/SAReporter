package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.DoubleCouncilDetailEntity;

@Repository
public interface DoubleCouncilDetailRepository extends PagingAndSortingRepository<DoubleCouncilDetailEntity, Integer> {

}
