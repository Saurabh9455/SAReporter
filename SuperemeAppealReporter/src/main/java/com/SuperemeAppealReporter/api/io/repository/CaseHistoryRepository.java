package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CaseHistory;

@Repository
public interface CaseHistoryRepository extends PagingAndSortingRepository<CaseHistory, Integer> {
	

}
