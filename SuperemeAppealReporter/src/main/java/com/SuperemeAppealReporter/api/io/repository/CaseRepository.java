package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import javax.persistence.Column;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CaseEntity;

@Repository
public interface CaseRepository extends PagingAndSortingRepository<CaseEntity, Integer>{
	
	public CaseEntity findByDocId(long docId);

/*	@Query(value="select c from CaseEntity c inner join CitationEntity cc inner join CitationCategoryEntity cce inner join CourtDetailEntity cde inner join CourtEntity ce"+
	             " where cce.citationCategoryName in (:caseCategoryList) and "+
			     " ce.courtType in (:courtCategoryList) and "+
	             " c.isLive in (:liveList) and "+
	             " c.isOverruled in (:overuledList)")*/
	@Query(value="select c from CaseEntity c join c.citationEntity cc  join cc.citationCategoryEntity cce  join c.courtDetailEntity cde  join cde.courtEntity ce"+
            " where cce.citationCategoryName in (:caseCategoryList) and "+
		     " ce.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
            " c.isOverruled in (:overuledList) and c.active = 1 ")
	public Page<CaseEntity> getCaseList(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
			@Param("caseCategoryList")  List <String> caseCategoryList,
			@Param("liveList") List<Boolean> liveList,
			@Param("overuledList") List<Boolean> overuledList);
/*	
	@Query(value="select c from CaseEntity c inner join CitationEntity cc inner join CitationCategoryEntity cce inner join CourtDetailEntity cde inner join CourtEntity ce "+
            " where cce.citationCategoryName in (:caseCategoryList) and "+
		     " ce.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
            " c.isOverruled in (:overuledList) and "+
            " (c.docId =(:searchValue) OR c.createdBy =(:searchValue)) ")*/
	
	
	
	/*@Query(value="select c from CaseEntity c join c.citationEntity cc  join cc.citationCategoryEntity cce  join c.courtDetailEntity cde  join cde.courtEntity ce"+
            " where cce.citationCategoryName in (:caseCategoryList) and "+
		     " ce.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
            " (c.docId =(:searchValue) OR c.createdBy =(:searchValue)) ")*/
	@Query(value="select c from CaseEntity c "+
            " where c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
            " c.createdBy =(:searchValue) and c.active = 1 ")
public Page<CaseEntity> getCaseList(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") String searchValue);
	
	
	@Query(value="select c from CaseEntity c "+
            " where c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
            " c.docId =(:searchValue) and c.active = 1 ")
public Page<CaseEntity> getCaseListInt(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") Long searchValue);
	
	@Query(value = "select original_pdf_path from case_entity where doc_id = ?1",nativeQuery=true)
	public String getPdfPathByDocId(long docId);
	
	@Query(value = "select count(*) from case_entity where is_active = 1",nativeQuery = true)
	public int getTotalCases();
	
	@Query(value = "select count(*) from case_entity where is_overruled =1 and is_active = 1",nativeQuery = true)
	public int getTotalOveruledCases();
	

	@Query(value = "select count(*) from case_entity where is_live =1 and is_active = 1",nativeQuery = true)
	public int getTotalLiveCases();
	
	@Query(value = "select * from case_entity where is_active = 1 and doc_id =?1 ",nativeQuery = true)
	public CaseEntity getCaseEntityByPrimaryKeyAndDocId(int docId);
	
	

}
