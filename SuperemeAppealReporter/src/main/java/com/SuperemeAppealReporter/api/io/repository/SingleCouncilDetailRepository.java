package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.SingleCouncilDetailEntity;

@Repository
public interface SingleCouncilDetailRepository extends PagingAndSortingRepository<SingleCouncilDetailEntity, Integer>{

}
