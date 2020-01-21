package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;

@Repository
public interface HeadnoteRepository extends PagingAndSortingRepository<HeadnoteEntity, Integer> {

	@Query(value = "select * from head_note where case_entity_id = ?1",nativeQuery=true)
	List<HeadnoteEntity> getRecord(Integer caseId);

}
