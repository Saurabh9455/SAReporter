package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.CaseEntity;

@Repository
public interface CaseRepository extends PagingAndSortingRepository<CaseEntity, Integer>{
	
	@Query(value = "select * from case_entity where doc_id = ?1 and is_active=1",nativeQuery=true)
	public CaseEntity findByDocId(long docId);

/*	@Query(value="select c from CaseEntity c inner join CitationEntity cc inner join CitationCategoryEntity cce inner join CourtDetailEntity cde inner join CourtEntity ce"+
	             " where cce.citationCategoryName in (:caseCategoryList) and "+
			     " ce.courtType in (:courtCategoryList) and "+
	             " c.isLive in (:liveList) and "+
	             " c.isOverruled in (:overuledList)")*/
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1"+
            " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
            " c.isOverruled in (:overuledList) order by c.createdDate desc")
	public Page<Object> getCaseList(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
			@Param("caseCategoryList")  List <String> caseCategoryList,
			@Param("liveList") List<Boolean> liveList,
			@Param("overuledList") List<Boolean> overuledList);
	
	
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1"+
            " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
            " c.isOverruled in (:overuledList) and c.updatedBy = :email order by c.createdDate desc")
	public Page<Object> getCaseListForDataEntry(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
			@Param("caseCategoryList")  List <String> caseCategoryList,
			@Param("liveList") List<Boolean> liveList,
			@Param("overuledList") List<Boolean> overuledList,@Param("email") String email);
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
	@Query(value= "select cE.docId, cE.appellant, cE.respondent, cE.courtDetailEntity.courtEntity.courtType, cE.caseHistoryEntity.decided_day, cE.caseHistoryEntity.decidedMonth, cE.caseHistoryEntity.decidedYear,cE.id from CaseEntity cE where cE.id in (:idList) order by field(cE.id,:idList)")
	public List<Object[]> getCaseEntityFieldsForPreparation(@Param("idList")List idList);
	
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1 "+
            " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
            " ( c.createdBy LIKE (:searchValue) OR c.appellant LIKE (:searchValue) OR c.respondent LIKE (:searchValue) ) and c.active = 1 order by c.createdDate desc")
public Page<Object> getCaseList(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") String searchValue);
	
	
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1 "+
            " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
            " ( c.createdBy LIKE (:searchValue) OR c.appellant LIKE (:searchValue) OR c.respondent LIKE (:searchValue) ) and c.active = 1 and c.updatedBy = :email order by c.createdDate desc")
public Page<Object> getCaseListForDataEntry(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") String searchValue,@Param("email") String email);
	
	
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"+
			 " c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1 "+
            " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
            " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
            " c.docId =(:searchValue) order by c.createdDate desc")
public Page<Object> getCaseListInt(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") Long searchValue);
	
	
	
	@Query(value="select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"+
			 " c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1 "+
           " and c.citationEntity.citationCategoryEntity.citationCategoryName in (:caseCategoryList) and "+
		     " c.courtDetailEntity.courtEntity.courtType in (:courtCategoryList) and "+
           " c.isLive in (:liveList) and "+
			" c.isOverruled in (:overuledList) and"+
           " c.docId =(:searchValue) and c.updatedBy = :email order by c.createdDate desc")
public Page<Object> getCaseListIntForDataEntry(Pageable pageable,@Param("courtCategoryList")List<String> courtCategoryList,
		@Param("caseCategoryList")  List <String> caseCategoryList,
		@Param("liveList") List<Boolean> liveList,
		@Param("overuledList") List<Boolean> overuledList,
		@Param("searchValue") Long searchValue, @Param("email") String email);
	
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
	
	
/*	@Query(value="select c from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1 ")
	public List<CaseEntity> getSupremeCourtCases();*/
	
	@Query(value="select count(*) from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1 ")
	public int getSupremeCourtCases();
	
/*	@Query(value="select c from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Divisional Bench'  ")
	public List<CaseEntity> getSupremeCourtDivisionalBenchCases();*/
	
	@Query(value="select count(*) from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Divisional Bench'  ")
	public int getSupremeCourtDivisionalBenchCases();
	
	/*@Query(value="select c from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Full Bench'  ")
	public List<CaseEntity> getSupremeCourtFullBenchCases();*/
	
	@Query(value="select count(*) from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Full Bench'  ")
	public int getSupremeCourtFullBenchCases();
	
/*	@Query(value="select c from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Third Bench'  ")
	public List<CaseEntity> getSupremeCourtThirdBenchCases();*/
	
	@Query(value="select count(*) from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'Supreme Court' and  c.active = 1"
		     + " and c.courtDetailEntity.courtBenchEntity.benchName = 'Third Bench'  ")
	public int getSupremeCourtThirdBenchCases();
	
	
	
/*	@Query(value="select c from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'High Court' and  c.active = 1 ")
	public List<CaseEntity> getHighCourtCases();*/
	
	@Query(value="select count(*) from CaseEntity c "+
		     " where c.courtDetailEntity.courtEntity.courtType = 'High Court' and  c.active = 1 ")
	public int getHighCourtCases();
	
/*	@Query(value = "select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType,  c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1")
	public Page<Object> findAllByActive(boolean active,Pageable page);*/
	@Query(value = "select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = 1 order by c.createdDate desc")
	public Page<Object> findAllByActive(boolean active,Pageable page);

	
	@Query(value = "select c.docId, c.appellant, c.respondent, c.citationEntity.citationCategoryEntity.citationCategoryName,"
			+ "c.courtDetailEntity.courtEntity.courtType, c.isOverruled, c.isLive, c.createdBy, c.createdDate, c.originalPdfPath from CaseEntity c where c.active = :active and c.updatedBy = :email order by c.createdDate desc")
	public Page<Object> findAllByActiveForDataEntryOperator(@Param("active")boolean active,@Param("email")String email,Pageable page);
	
	/****************************************ALL USER SEARCH METHODS***********************************/
	/*
	 * 
[Appellent] VS [Respondent] ([Court Category in short form e.g. SC/HC]) D/d. [Decided Date d.m.year]
[H1 actname1] > [H1 section1] > [H1 actname2] > [H1 section2] > [H1 actname3] > [H1 section3] > [H1 Topic] with [H2 actname1] > [H2 section1] > [H2 actname2] > [H2 section2] > [H2 actname3] > [H2 section3] > [H2 Topic] with ... so on



H1 indicates Headnote 1
H2 indicates Headnote 2
	 */
	/**For DASHBOARD SEARCH TYPE**/

	@Query(value = "select distinct c.id "
			+ " from  CaseEntity c LEFT OUTER JOIN c.headNoteEntitySet hes  where ( upper(hes.topic) like %:searchValue% or upper(c.appellant) like %:searchValue% or upper(c.respondent) like %:searchValue%) and c.active = 1 and c.isLive = 1")
	public Page<Object> dashboardSearchV2(Pageable page,@Param("searchValue") String searchValue);

	@Query(value = "select distinct c.id "
			+ " from  CaseEntity c where c.docId =:searchValue  and c.active = 1 and c.isLive = 1")
	public Page<Object> dashboardSearchForDocIdV2(Pageable page,@Param("searchValue") long searchValue);
	
	
	/**FOR MAIN SEARCH TYPE**/

	@Query(value = "select c.id, c.appellant,c.respondent,c.courtDetailEntity.courtEntity.courtType, hes.actname1, "
			+ " hes.section1, hes.actname2, hes.section2, hes.actname3, hes.section3, hes.topic, "
			+ " c.caseHistoryEntity.decided_day,"
			+ " c.caseHistoryEntity.decidedMonth,c.caseHistoryEntity.decidedYear, c.docId "
			+ " from  CaseEntity c LEFT OUTER JOIN c.headNoteEntitySet hes LEFT OUTER JOIN c.casesReferredEntitySet cre "
			+ " LEFT OUTER JOIN c.courtDetailEntity.courtEntity cne LEFT OUTER JOIN c.doubleCouncilDetailEntity  dcde LEFT OUTER JOIN c.singleCouncilDetailEntity  scde  "
			+ " where (hes.topic like %:topic% or hes.topic=NULL or hes.topic='none') and "
			+ " (hes.headnote like %:headnote% or hes.headnote=NULL or hes.headnote='none') and "
			+ " (c.judgeName like %:judgeName% OR c.courtDetailEntity.allJudges like %:judgeName%) "
			+ " and c.caseHistoryEntity.caseNumber like %:caseNumber% and c.caseHistoryEntity.decidedYear like %:decidedYear% and  c.caseHistoryEntity.decidedYear like %:decidedYear2%"
			+ " and c.appellant like %:appellant% "
			+ " and c.respondent like %:respondent% and "
			+ " (hes.actname1 like %:actname% or hes.actname1=NULL or hes.actname1='none') and (hes.actname2 like %:actname% or hes.actname2=NULL OR hes.actname2='none' ) and (hes.actname3 like %:actname% or hes.actname3=NULL or hes.actname3='none') and "
			+ " (hes.section1 like %:section% or hes.section1=NULL or hes.section1='none' ) and (hes.section2 like %:section% or hes.section2=NULL OR hes.section2='none')  and (hes.section3 like %:section% or hes.section3=NULL or hes.section3='none') "
			+ " and c.caseResult like %:caseResult%"
			+ " and c.caseHistoryEntity.decided_day like %:decided_day% "
			+ " and c.caseHistoryEntity.decidedMonth like %:decidedMonth%  "
			
			+ " and (c.caseHistoryEntity.year between :startYear and :endYear)  "
			+ " and c.judgementOrder like %:judgment% "
	        + " and cne.courtType like %:courtName% "
			+ " and ( (dcde.advocateForAppellant like %:advocate% OR dcde.advocateForAppellant=NULL OR dcde.advocateForAppellant='none' ) "
			+ " AND (dcde.advocateForRespondent like %:advocate% OR dcde.advocateForRespondent=NULL OR dcde.advocateForRespondent='none' ) "
			+ " AND (dcde.extraCouncilDetails like %:advocate% OR dcde.extraCouncilDetails=NULL OR dcde.extraCouncilDetails='none' ) "
			+ " AND (scde.petionerName like %:advocate% OR scde.petionerName=NULL OR scde.petionerName='none' ) "
			+ ") AND c.active = 1 AND c.isLive = 1")
   public Page<Object> mainSearch(Pageable page,@Param("topic") String topic, 
		   @Param("headnote") String headnote,
		   @Param("judgeName") String judgeName,
		   @Param("caseNumber") String caseNumber,
		   @Param("appellant") String appellant,
		   @Param("respondent") String respondent,
		   @Param("actname") String actname,
		   @Param("section") String section,
		   @Param("decided_day") String decided_day,
		   @Param("decidedMonth") String decidedMonth,
		   @Param("decidedYear") String decidedYear, 
		   @Param("startYear") String startYear,
		   @Param("endYear") String endYear,
		   @Param("caseResult") String caseResult,
		   @Param("judgment") String judgement,
		   @Param("advocate") String advocate,
		   @Param("courtName") String courtName,
		   @Param("decidedYear2") String decidedYear2
		 
		   
		   
		   
		   
		   );
	/*
	 * pageableRequest, mainSearchTopic, 
				 								mainSearchHeadNote, mainSearchJudge, mainSearchCaseNumber, mainSearchPrimaryAppellant,
				 								mainSearchPrimaryRespondent, mainSearchActName, 
				 								mainSearchSection, day, month, year, mainSearchStartYear, mainSearchEndYear,
				 								mainSearchCaseResult,
				 								mainSearchCourtName,mainSearchDecidedYear,
				 								mainSearchAdvocate,
				 								mainSearchJudgement);
	 */
	
/*	@Query(value = ":query")
	 public Page<Object> dashBoardSearch(Pageable page,@Param("query") String query);
	*/
	
	
	@Query(value = "select c.id, c.appellant,c.respondent,c.courtDetailEntity.courtEntity.courtType, hes.actname1, "
			+ " hes.section1, hes.actname2, hes.section2, hes.actname3, hes.section3, hes.topic, "
			+ " c.caseHistoryEntity.decided_day,"
			+ " c.caseHistoryEntity.decidedMonth,c.caseHistoryEntity.decidedYear, c.docId "
			+ " from  CaseEntity c LEFT OUTER JOIN c.headNoteEntitySet hes LEFT OUTER JOIN c.casesReferredEntitySet cre "
			+ " LEFT OUTER JOIN c.courtDetailEntity.courtEntity cne LEFT OUTER JOIN c.citationEntity cte LEFT OUTER JOIN c.citationEntity.journalEntity cjte  where "
			+ " cte.pageNumber like %:pageNumber% AND cte.year like %:year% AND cjte.journalType like %:journal% and c.active = 1 and c.isLive = 1")
	public Page<Object> citationSearch(Pageable page,@Param("pageNumber")String pageNumber,@Param("journal")String journal,
			@Param("year")String year);
	
	
	
	
	@Query(value = "select c.id, c.appellant,c.respondent,c.courtDetailEntity.courtEntity.courtType, hes.actname1, "
			+ " hes.section1, hes.actname2, hes.section2, hes.actname3, hes.section3, hes.topic, "
			+ " c.caseHistoryEntity.decided_day,"
			+ " c.caseHistoryEntity.decidedMonth,c.caseHistoryEntity.decidedYear, c.docId "
			+ " from  CaseEntity c LEFT OUTER JOIN c.headNoteEntitySet hes LEFT OUTER JOIN c.casesReferredEntitySet cre "
			+ " LEFT OUTER JOIN c.courtDetailEntity.courtEntity cne where "
			+ " (hes.actname1 like %:actName% AND hes.actname2 like %:actName% AND hes.actname3 like %:actName%) "
			+ " AND (hes.section1 like %:section% AND hes.section2 like %:section% AND hes.section3 like %:section%) and c.active = 1 and c.isLive = 1")
	public Page<Object> statueSearch(Pageable page,@Param("actName")String actName,
			@Param("section")String section);

	@Query(value = "select appellant,respondent,doc_id from case_entity where id = ?1 ",nativeQuery=true)
	public List<String[]> findAppResById(Integer caseId);
	
	@Query(value = "select * from case_entity where id in ?1 ",nativeQuery=true)
	public List<CaseEntity> findAppResByIdV2(List<Integer> caseIdList);
	
/*	@Query(value = "select * from case_entity where id in ?1 ",nativeQuery=true)
	public List<CaseEntity> findAppResByIdV2(List<Integer> caseIdList);*/

	@Query(value = "select ch.decided_day,ch.decided_month,ch.decided_year from case_entity ce left join case_history ch on ce.case_history_entity_id = ch.id where ce.id = ?1",nativeQuery=true)
	public List<String[]> findRecordCaseHisotry(Integer caseId);
	
	@Query(value = "select doc_id from case_entity where is_active = 1  ORDER BY id DESC LIMIT 1",nativeQuery = true)
	public Object getLatestDocId();
	
}

