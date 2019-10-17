package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.SuperemeAppealReporter.api.io.entity.DocIdGenerator;

public interface DocIdGeneratorRepository extends PagingAndSortingRepository<DocIdGenerator, Integer>{
	
	@Query(value = "select max(id) from doc_id_generator",nativeQuery = true)
	public int giveLastDocId();

}
