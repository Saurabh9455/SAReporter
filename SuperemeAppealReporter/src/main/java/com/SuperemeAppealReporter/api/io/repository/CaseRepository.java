package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CaseEntity;

@Repository
public interface CaseRepository extends PagingAndSortingRepository<CaseEntity, Integer>{

}
