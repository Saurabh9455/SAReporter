package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CasesRefferedEntity;

@Repository
public interface CasesRefferedRepository extends PagingAndSortingRepository<CasesRefferedEntity, Integer> {

}
