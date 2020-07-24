package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;

@Repository
public interface HeadnoteRepository extends PagingAndSortingRepository<HeadnoteEntity, Integer> {

	@Query(value = "select * from head_note where case_entity_id = ?1",nativeQuery=true)
	List<HeadnoteEntity> getRecord(Integer caseId);

	/*@Query(value = "select h.actname1, h.actname2,h.actname3,h.actname4 from HeadnoteEntity h where upper(h.actname1) like %:actname% OR "
			     + "upper(h.actname2) like %:actname% OR upper(h.actname3) like %:actname% OR upper(h.actname4) like %:actname% ")
	public List<String[]> searchActname(@Param("actname")String actname,Pageable pageableRequest);*/
	
	@Query(value = "select  h.actname1, h.actname2,h.actname3,h.actname4 from HeadnoteEntity h where ( upper(h.actname1) like %:actname% OR "
		     + "upper(h.actname2) like %:actname% OR upper(h.actname3) like %:actname% OR upper(h.actname4) like %:actname% ) and h.caseEntity.isLive=1 ",countQuery=" select count(h.actname1) from HeadnoteEntity h where h.caseEntity.isLive=1")
public Page<String[]> searchActname(@Param("actname")String actname,Pageable pageableRequest);

@Query(value = "select  h.topic from HeadnoteEntity h where upper(h.topic) like %:topic% and h.caseEntity.isLive=1 ",countQuery=" select count(h.topic) from HeadnoteEntity h where h.caseEntity.isLive=1")
public Page<String> searchTopic(@Param("topic")String topic,Pageable pageableRequest);
} 
