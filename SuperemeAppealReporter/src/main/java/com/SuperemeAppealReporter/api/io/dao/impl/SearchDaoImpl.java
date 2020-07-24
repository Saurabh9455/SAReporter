package com.SuperemeAppealReporter.api.io.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.io.dao.SearchDao;
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;

@Component

@SuppressWarnings({"rawtypes","unchecked"})

public class SearchDaoImpl implements SearchDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<Object> performMainSearch(SearchRequest searchRequest, int pageNumber, int perPage,boolean forSize) {
		
		StringBuffer query = new StringBuffer();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	/*	query.append(" select distinct ce.id "
				+ " from case_entity ce "
				+ " left join court_detail cde on ce.court_detail_entity_id = cde.id "
				+ " left join court c on cde.court_entity_id = c.id "
				+ " left join court_branch cb on cde.court_branch_entity_id = cb.id "
				+ " left join court_bench cben on cde.court_bench_entity_id = cben.id "
				+ " left join citation ct on ce.citation_entity_id = ct.id "
				+ " left join citation_category cc on ct.citation_category_entity_id = cc.id "
				+ " left join journal j on ct.journal_entity_id = j.id "
				+ " left join additonal_appellant_respondent_entity apre on ce.id = apre.case_entity_id "
				+ " left join case_history ch on ce.case_history_entity_id = ch.id "
				+ " left join cases_reffered cr on ce.id = cr.case_entity_id "
				+ " left join head_note hn on ce.id = hn.case_entity_id "
				+ " left join double_council_detail_entity dcde on ce.double_council_detail_entity_id = dcde.id "
				+ " left join single_council_detail_entity scde on ce.single_council_detail_entity_id = scde.id where ");
		*/
		query.append(" SELECT DISTINCT A.ID FROM "
				+ " ( SELECT ce.id,ce.appellant as appellant_1, "
				+ "   DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
				+ "   FROM case_entity ce "
				+ "   left join court_detail cde on ce.court_detail_entity_id = cde.id "
				+ "   left join court c on cde.court_entity_id = c.id "
				+ "   left join court_branch cb on cde.court_branch_entity_id = cb.id "
				+ "   left join court_bench cben on cde.court_bench_entity_id = cben.id "
				+ "   left join citation ct on ce.citation_entity_id = ct.id "
				+ "   left join citation_category cc on ct.citation_category_entity_id = cc.id "
				+ "   left join journal j on ct.journal_entity_id = j.id "
				+ "   left join additonal_appellant_respondent_entity apre on ce.id = apre.case_entity_id "
				+ "   left join case_history ch on ce.case_history_entity_id = ch.id "
				+ "   left join cases_reffered cr on ce.id = cr.case_entity_id "
				+ "   left join head_note hn on ce.id = hn.case_entity_id "
				+ "   left join double_council_detail_entity dcde on ce.double_council_detail_entity_id = dcde.id "
				+ "   left join single_council_detail_entity scde on ce.single_council_detail_entity_id = scde.id where ");
		
	
	
		
		boolean flag = false;
		if(searchRequest.getMainSearchCourtId()!=null)
		{
			
			query.append(" c.court_type = :COURT_NAME  ");
			paramMap.put("COURT_NAME", searchRequest.getMainSearchCourtId().trim());
			flag = true;
		}
		if (searchRequest.getStartsWithAlphabet() != null)
		{
			if(flag)
			{
				query.append(" and ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET", searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			else
			{
				query.append(" ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET",  searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchDecisionDate()!=null)
		{
			String date[] =searchRequest.getMainSearchDecisionDate().split("/");
		    String	day = date[0];
	    	String	month = date[1];
			String year = date[2];
			
			System.out.println(day+"/"+month+"/"+year);
			if(flag)
			{
				query.append(" and ch.decided_day = :DECIDED_DAY and ch.decided_month = :DECIDED_MONTH and ch.decided_year = :DECIDED_YEAR ");
				paramMap.put("DECIDED_DAY", day);
				paramMap.put("DECIDED_MONTH", month);
				paramMap.put("DECIDED_YEAR", year);
			}
			else
			{
				query.append("  ch.decided_day = :DECIDED_DAY and ch.decided_month = :DECIDED_MONTH and ch.decided_year = :DECIDED_YEAR ");
				paramMap.put("DECIDED_DAY", day);
				paramMap.put("DECIDED_MONTH", month);
				paramMap.put("DECIDED_YEAR", year);
			}
			flag = true;
			
		}
		if (searchRequest.getMainSearchCaseNumber()!=null)
		{
			if(flag)
			{
				query.append(" and ch.case_number LIKE :CASE_NUMBER ");
				paramMap.put("CASE_NUMBER", "%"+searchRequest.getMainSearchCaseNumber().trim()+"%");
			}
			else
			{
				query.append(" ch.case_number LIKE :CASE_NUMBER ");
				paramMap.put("CASE_NUMBER", "%"+searchRequest.getMainSearchCaseNumber().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchDecidedYear()!=null)
		{
			if(flag)
			{
				query.append(" and ch.decided_year = :ONLY_DECIDED_YEAR ");
				paramMap.put("ONLY_DECIDED_YEAR", searchRequest.getMainSearchDecidedYear().trim());
			}
			else
			{
				query.append(" ch.decided_year = :ONLY_DECIDED_YEAR ");
				paramMap.put("ONLY_DECIDED_YEAR", searchRequest.getMainSearchDecidedYear().trim());
			}
			flag = true;
		}
		if (searchRequest.getMainSearchPrimaryAppellant()!=null)
		{
			if(flag)
			{
				query.append(" and ce.appellant LIKE :APPELLANT ");
				paramMap.put("APPELLANT", "%"+searchRequest.getMainSearchPrimaryAppellant().trim()+"%");
			}
			else
			{
				query.append(" ce.appellant LIKE :APPELLANT ");
				paramMap.put("APPELLANT", "%"+searchRequest.getMainSearchPrimaryAppellant().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchPrimaryRespondent()!=null)
		{
			if(flag)
			{
				query.append(" and ce.respondent LIKE :RESPONDENT ");
				paramMap.put("RESPONDENT", "%"+searchRequest.getMainSearchPrimaryRespondent().trim()+"%");
				
			}
			else
			{
				query.append("  ce.respondent LIKE :RESPONDENT ");
				paramMap.put("RESPONDENT", "%"+searchRequest.getMainSearchPrimaryRespondent().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchActName() != null)
		{
			if(flag)
			{
				query.append(" and ( upper(hn.actname_1) LIKE :ACTNAME or upper(hn.actname_2) LIKE :ACTNAME or upper(hn.actname_3) LIKE :ACTNAME )   ");
				paramMap.put("ACTNAME", "%"+searchRequest.getMainSearchActName().trim().toUpperCase()+"%");
			}
			else
			{
				query.append("  ( upper(hn.actname_1) LIKE :ACTNAME or upper(hn.actname_2) LIKE :ACTNAME or upper(hn.actname_3) LIKE :ACTNAME )   ");
				paramMap.put("ACTNAME", "%"+searchRequest.getMainSearchActName().trim().toUpperCase()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchSection()!=null)
		{
			if(flag)
			{
				query.append(" and ( hn.section_1 LIKE :SECTION or hn.section_2 =:SECTION or hn.section_3 = :SECTION )   ");
				paramMap.put("SECTION", "%"+searchRequest.getMainSearchSection().trim()+"%");
			}
			else
			{
				query.append("  ( hn.section_1 LIKE :SECTION or hn.section_2 LIKE :SECTION or hn.section_3 LIKE :SECTION )   ");
				paramMap.put("SECTION", "%"+searchRequest.getMainSearchSection().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchTopic()!=null)
		{
			if(flag)
			{
				query.append(" and hn.topic LIKE :TOPIC  ");
				paramMap.put("TOPIC", "%"+searchRequest.getMainSearchTopic().trim()+"%");
			}
			else
			{
				query.append("  hn.topic LIKE :TOPIC  ");
				paramMap.put("TOPIC", "%"+searchRequest.getMainSearchTopic().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchHeadNote()!=null)
		{
			if(flag)
			{
				query.append(" and hn.headnote LIKE :HEADNOTE  ");
				paramMap.put("HEADNOTE", "%"+searchRequest.getMainSearchHeadNote().trim()+"%");
			}
			else
			{
				query.append(" hn.headnote LIKE :HEADNOTE  ");
				paramMap.put("HEADNOTE", "%"+searchRequest.getMainSearchHeadNote().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getMainSearchJudgement()!=null)
		{
			if(flag)
			{
				query.append(" and ce.jdugement_order LIKE :JUDMENT_ORDER  ");
				paramMap.put("JUDMENT_ORDER", "%"+searchRequest.getMainSearchJudgement().trim()+"%");
			}
			else
			{
				query.append("  ce.jdugement_order LIKE :JUDMENT_ORDER  ");
				paramMap.put("JUDMENT_ORDER", "%"+searchRequest.getMainSearchJudgement().trim()+"%");
			}
			flag = true;
		}
		if(searchRequest.getMainSearchAdvocate()!=null)
		{
			if(flag)
			{
				query.append(" and ( dcde.advocate_for_appellant LIKE :ADVOCATE or dcde.advocate_for_respondent LIKE :ADVOCATE or dcde.extra_council_details LIKE :ADVOCATE or scde.petitioner_name LIKE :ADVOCATE ) ");
				paramMap.put("ADVOCATE","%"+searchRequest.getMainSearchAdvocate().trim()+"%");
			}
			else
			{
				query.append("  dcde.advocate_for_appellant LIKE :ADVOCATE or dcde.advocate_for_respondent LIKE :ADVOCATE or dcde.extra_council_details LIKE :ADVOCATE or scde.petitioner_name LIKE :ADVOCATE  ");
				paramMap.put("ADVOCATE","%"+searchRequest.getMainSearchAdvocate().trim()+"%");	}
			flag = true;
		}
		if(searchRequest.getMainSearchJudge()!=null)
		{
			if(flag)
			{
				query.append(" and ( ce.judge_name = :JUDGE_NAME or cde.all_judges LIKE  :JUDGE_NAME  ) ");
				paramMap.put("JUDGE_NAME","%"+searchRequest.getMainSearchJudge().trim()+"%");
			}
			else
			{
				query.append("  ce.judge_name = :JUDGE_NAME or  cde.all_judges LIKE :JUDGE_NAME   ");
				paramMap.put("JUDGE_NAME","%"+searchRequest.getMainSearchJudge().trim()+"%");
			}
			flag = true;
		}
		if(searchRequest.getMainSearchCaseResult()!=null)
		{
			if(flag)
			{
				query.append(" and ce.case_result LIKE :CASE_RESULT ");
				paramMap.put("CASE_RESULT","%"+searchRequest.getMainSearchCaseResult().trim()+"%");
				
			}
			else
			{
				query.append("  ce.case_result LIKE :CASE_RESULT ");
				paramMap.put("CASE_RESULT","%"+searchRequest.getMainSearchCaseResult().trim()+"%");
			}
			flag = true;
		}
		if(searchRequest.getMainSearchStartYear()!=null)
		{
			if(flag)
			{
				query.append(" and ch.decided_year  >= :START_YEAR ");
				paramMap.put("START_YEAR",searchRequest.getMainSearchStartYear());
			}
			else
			{
				query.append(" ch.decided_year  >= :START_YEAR ");
				paramMap.put("START_YEAR",searchRequest.getMainSearchStartYear());
			}
			flag = true;
		}
		if(searchRequest.getMainSearchEndYear()!=null)
		{
			if(flag)
			{
				query.append(" and ch.decided_year  <= :END_YEAR ");
				paramMap.put("END_YEAR",searchRequest.getMainSearchEndYear());
			}
			else
			{
				query.append("  ch.decided_year  <= :END_YEAR ");
				paramMap.put("END_YEAR",searchRequest.getMainSearchEndYear());
			}
			flag = true;
		}
		
	
		
		if(flag)
		{
		query.append("  and ce.is_active =1 and ce.is_live =1  ");
		}
		else
		{
			query.append(" ce.is_active =1 and ce.is_live =1  ");
		}
		
		
		/*query.append(" )A ORDER BY A.DATE_1 ");*/
		
		if (searchRequest.getDecidedDateOrder() != null) {

			if (searchRequest.getDecidedDateOrder().equals("a")) {
				query.append("  )A ORDER BY A.DATE_1 ");
			} else {
				query.append("  )A ORDER BY A.DATE_1 desc");
			}
		}

		else if (searchRequest.getAppellantNameOrder() != null) {

			if (searchRequest.getAppellantNameOrder().equals("a")) {
				query.append("  )A ORDER BY A.appellant_1  ");
			} else {
				query.append("  )A ORDER BY A.appellant_1 desc");
			}
		}

		else {
			query.append("  )A ORDER BY A.DATE_1 ");
		}
		
	int startRow = pageNumber==0 ? 0 : pageNumber*8;
	int endRow = 8;
	
	if(!forSize)
	{
	
	
	query.append(" LIMIT :START_ROW , :END_ROW ");
	paramMap.put("START_ROW",startRow);
	paramMap.put("END_ROW",endRow);
	}
	
		Query qry = entityManager.createNativeQuery(query.toString());
		
		setQueryParameter(qry, paramMap);
		
		
		List ls = qry.getResultList();

		return ls;
	}
	
	public static Query setQueryParameter(Query query, Map<String, Object> queryPara) {
		Iterator<Entry<String, Object>> entries = queryPara.entrySet().iterator();
		while (entries.hasNext()) {
		    Entry<String, Object> entry = entries.next();
		    query.setParameter((String) entry.getKey(), entry.getValue());
		}
		
		return query;
	}

	@Override
	public List<Object> performCitationSearch(SearchRequest searchRequest, int pageNumber, int perPage,boolean forSize,boolean isOtherCitation) {
		
		List ls = null;
		
		/**For Other Citation Search**/
		if(isOtherCitation){
			
			StringBuffer query = new StringBuffer();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			query.append(" SELECT DISTINCT A.ID FROM"
					+ "   ( SELECT ce.id,ce.appellant as appellant_1, DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
					+ "     from case_entity ce "
					+ "     left join case_history ch on ce.case_history_entity_id = ch.id "
					+ "     left join citation c on ce.citation_entity_id = c.id where "
					        );
			
		
			
			
			String year = searchRequest.getOtherCitationSearchYear();
			String volume = searchRequest.getOtherCitationSearchVolume();
			String journalName = searchRequest.getOtherCitationSearchJournal();
			String otherCitationPageNumber = searchRequest.getOtherCitationSearchPageNumber(); 
			
		
			if(volume==null){
				volume = "";
			}
		
			String searchValue  = (year.trim()+" ("+volume.trim()+") "+journalName.trim()+" "+otherCitationPageNumber.trim()).trim();
			
			if(volume.equals("")){
				searchValue  = (year.trim()+" "+journalName.trim()+" "+otherCitationPageNumber.trim()).trim();
					
			}
			searchValue = searchValue.trim() +" ";
			query.append(" c.other_citation LIKE :SEARCH_VALUE and ");
			paramMap.put("SEARCH_VALUE","%"+searchValue+"%");
			
            
			query.append("   ce.is_active =1 and ce.is_live =1  ");
			
			/*query.append(" )A ORDER BY A.DATE_1 ");*/
			
			
			if (searchRequest.getDecidedDateOrder() != null) {

				if (searchRequest.getDecidedDateOrder().equals("a")) {
					query.append("  )A ORDER BY A.DATE_1 ");
				} else {
					query.append("  )A ORDER BY A.DATE_1 desc");
				}
			}

			else if (searchRequest.getAppellantNameOrder() != null) {

				if (searchRequest.getAppellantNameOrder().equals("a")) {
					query.append("  )A ORDER BY A.appellant_1  ");
				} else {
					query.append("  )A ORDER BY A.appellant_1 desc");
				}
			}

			else {
				query.append("  )A ORDER BY A.DATE_1 ");
			}
			
			int startRow = pageNumber==0 ? 0 : pageNumber*8;
			int endRow = 8;
			
			if(!forSize)
			{
		
		
			query.append(" LIMIT :START_ROW , :END_ROW ");
			paramMap.put("START_ROW",startRow);
			paramMap.put("END_ROW",endRow);
			}
			Query qry = entityManager.createNativeQuery(query.toString());
			setQueryParameter(qry, paramMap);
			
			 ls = qry.getResultList();
			
			
		}
		
		/**For MainCitation Search **/
		else
		{
			
        StringBuffer query = new StringBuffer();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		boolean isSecondaryCitationCase = false;
		if(searchRequest.getCitationSearchJournal()!=null && searchRequest.getOtherCitationSearchJournal().equalsIgnoreCase("SAR Online")){
			isSecondaryCitationCase  = true;
			query.append(" SELECT DISTINCT A.ID FROM"
					+ "   ( SELECT ce.id,ce.appellant as appellant_1, DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
					+ "     from case_entity ce "
					+ "     left join case_history ch on ce.case_history_entity_id = ch.id "
					+ "     left join citation c on ce.citation_entity_id = c.id "
					+ "     left join citation_category cc on c.citation_category_entity_id = cc.id"
					+ "     left join journal j on c.journal_entity_id = j.id where ");
			
		}
		else {	
		query.append(" SELECT DISTINCT A.ID FROM"
				+ "   ( SELECT ce.id,ce.appellant as appellant_1, DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
				+ "     from case_entity ce "
				+ "     left join case_history ch on ce.case_history_entity_id = ch.id "
				+ "     left join citation c on ce.citation_entity_id = c.id "
				+ "     left join citation_category cc on c.citation_category_entity_id = cc.id"
				+ "     left join secondary_citation scc on c.secondary_citation_entity_id = scc.id"
				+ "     left join journal j on c.journal_entity_id = j.id where ");
		
		}
	/*	query.append(" select distinct ce.id "
				+ " from case_entity ce "
				+ " left join citation c on ce.citation_entity_id = c.id "
				+ " left join citation_category cc on c.citation_category_entity_id = cc.id"
				+ " left join journal j on c.journal_entity_id = j.id where ");*/
		
		boolean flag = false;
		if(searchRequest.getCitationSearchJournal()!=null)
		{
			if(flag)
			{
				query.append(" and j.journal_type LIKE :JOURNAL_TYPE ");
				paramMap.put("JOURNAL_TYPE","%"+searchRequest.getCitationSearchJournal().trim()+"%");
				
			}
			else
			{
				query.append(" j.journal_type LIKE :JOURNAL_TYPE ");
				paramMap.put("JOURNAL_TYPE","%"+searchRequest.getCitationSearchJournal().trim()+"%");
			}
			flag = true;
		}
		if(searchRequest.getCitationCategory()!=null)
		{
			if(flag)
			{
				query.append(" and cc.category_name = :CATEGORY_NAME ");
				paramMap.put("CATEGORY_NAME",searchRequest.getCitationCategory().trim());
				
			}
			else
			{
				query.append(" cc.category_name = :CATEGORY_NAME ");
				paramMap.put("CATEGORY_NAME",searchRequest.getCitationCategory().trim());
			}
			flag = true;
		}
		if (searchRequest.getStartsWithAlphabet() != null)
		{
			if(flag)
			{
				query.append(" and ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET", searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			else
			{
				query.append(" ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET",  searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			flag = true;
		}
		
		if(isSecondaryCitationCase) {
			if(searchRequest.getCitationSearchPageNumber()!=null)
			{
				if(flag)
				{
					query.append(" and scc.page_number = :PAGE_NUMBER ");
					paramMap.put("PAGE_NUMBER",searchRequest.getCitationSearchPageNumber());
				}
				else
				{
					query.append("  scc.page_number = :PAGE_NUMBER ");
					paramMap.put("PAGE_NUMBER",searchRequest.getCitationSearchPageNumber());
				}
				flag = true;
			}
			if(searchRequest.getCitationSearchYear()!=null)
			{
				if(flag)
				{
					query.append(" and scc.year  = :YEAR ");
					paramMap.put("YEAR",searchRequest.getCitationSearchYear());
				}
				else
				{
					query.append("  scc.year  = :YEAR ");
					paramMap.put("YEAR",searchRequest.getCitationSearchYear());
				}
				flag = true;
			}
		
		}
		else {
			
		
		if(searchRequest.getCitationSearchPageNumber()!=null)
		{
			if(flag)
			{
				query.append(" and c.page_number = :PAGE_NUMBER ");
				paramMap.put("PAGE_NUMBER",searchRequest.getCitationSearchPageNumber());
			}
			else
			{
				query.append("  c.page_number = :PAGE_NUMBER ");
				paramMap.put("PAGE_NUMBER",searchRequest.getCitationSearchPageNumber());
			}
			flag = true;
		}
		if(searchRequest.getCitationSearchYear()!=null)
		{
			if(flag)
			{
				query.append(" and c.year  = :YEAR ");
				paramMap.put("YEAR",searchRequest.getCitationSearchYear());
			}
			else
			{
				query.append("  c.year  = :YEAR ");
				paramMap.put("YEAR",searchRequest.getCitationSearchYear());
			}
			flag = true;
		}
		}
		
	
		
		if(flag)
		{
		query.append("  and ce.is_active =1 and ce.is_live =1  ");
		
		}
		else
		{
			query.append(" ce.is_active =1 and ce.is_live =1  ");
		}
		
		/*query.append(" )A ORDER BY A.DATE_1 ");*/
		
		if (searchRequest.getDecidedDateOrder() != null) {

			if (searchRequest.getDecidedDateOrder().equals("a")) {
				query.append("  )A ORDER BY A.DATE_1 ");
			} else {
				query.append("  )A ORDER BY A.DATE_1 desc");
			}
		}

		else if (searchRequest.getAppellantNameOrder() != null) {

			if (searchRequest.getAppellantNameOrder().equals("a")) {
				query.append("  )A ORDER BY A.appellant_1  ");
			} else {
				query.append("  )A ORDER BY A.appellant_1 desc");
			}
		}

		else {
			query.append("  )A ORDER BY A.DATE_1 ");
		}

		
		int startRow = pageNumber==0 ? 0 : pageNumber*8;
		int endRow = 8;
		
		if(!forSize)
		{
		query.append(" LIMIT :START_ROW , :END_ROW ");
		paramMap.put("START_ROW",startRow);
		paramMap.put("END_ROW",endRow);
		}
		Query qry = entityManager.createNativeQuery(query.toString());
		setQueryParameter(qry, paramMap);
		
		 ls = qry.getResultList();
		}
		return ls;
	}

	@Override
	public List<Object> performStatueSearch(SearchRequest searchRequest, int pageNumber, int perPage,boolean forSize) {
		
        StringBuffer query = new StringBuffer();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	/*	query.append(" select distinct ce.id "
				+ " from case_entity ce "
				+ " left join head_note hn on ce.id = hn.case_entity_id where");
		
		*/
		query.append(" SELECT DISTINCT A.ID FROM "
				+ "     ( SELECT ce.id,ce.appellant as appellant_1, DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
				+ "       from case_entity ce "
				+ "       left join case_history ch on ce.case_history_entity_id = ch.id "
				+ "       left join head_note hn on ce.id = hn.case_entity_id where");
		
	
		boolean flag = false;
		if (searchRequest.getStatueSearchActName() != null)
		{
			if(flag)
			{
				query.append(" and ( upper(hn.actname_1) LIKE :ACTNAME or upper(hn.actname_2) LIKE :ACTNAME or upper(hn.actname_3) LIKE :ACTNAME or upper(hn.actname_4) LIKE :ACTNAME )   ");
				paramMap.put("ACTNAME", "%"+searchRequest.getStatueSearchActName().trim().toUpperCase()+"%");
			}
			else
			{
				query.append("  ( upper(hn.actname_1) LIKE :ACTNAME or upper(hn.actname_2) LIKE :ACTNAME or upper(hn.actname_3) LIKE :ACTNAME or upper(hn.actname_4) LIKE :ACTNAME)   ");
				paramMap.put("ACTNAME", "%"+searchRequest.getStatueSearchActName().trim().toUpperCase()+"%");
			}
			flag = true;
		}
		if (searchRequest.getStartsWithAlphabet() != null)
		{
			if(flag)
			{
				query.append(" and ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET", searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			else
			{
				query.append(" ce.appellant LIKE :STARTALBHABET ");
				paramMap.put("STARTALBHABET",  searchRequest.getStartsWithAlphabet().trim()+"%");
			}
			flag = true;
		}
		if (searchRequest.getStatueSearchSection()!=null)
		{
			if(flag)
			{
				query.append(" and ( hn.section_1 LIKE :SECTION or hn.section_2 LIKE :SECTION or hn.section_3 LIKE :SECTION or hn.section_4  LIKE :SECTION)   ");
				paramMap.put("SECTION", "%"+searchRequest.getStatueSearchSection().trim()+"%");
			}
			else
			{
				query.append("  ( hn.section_1 LIKE :SECTION or hn.section_2 LIKE :SECTION or hn.section_3 LIKE :SECTION or hn.section_4  LIKE :SECTION )   ");
				paramMap.put("SECTION", "%"+searchRequest.getStatueSearchSection().trim()+"%");
			}
			flag = true;
		}
		
		/**added for search on the basis of new parameters**/
		if (searchRequest.getStatueSearchOrder()!=null)
		{
			if(flag)
			{
				query.append(" and ( hn.order_1 LIKE :ORDER or hn.order_2 LIKE :ORDER or hn.order_3 LIKE :ORDER or hn.order_4  LIKE :ORDER)   ");
				paramMap.put("ORDER", "%"+searchRequest.getStatueSearchOrder().trim()+"%");
			}
			else
			{
				query.append("  ( hn.order_1 LIKE :ORDER or hn.order_2 LIKE :ORDER or hn.order_3 LIKE :ORDER or hn.order_4  LIKE :ORDER )   ");
				paramMap.put("ORDER", "%"+searchRequest.getStatueSearchOrder().trim()+"%");
			}
			flag = true;
		}
		
		if (searchRequest.getStatueSearchRule()!=null)
		{
			if(flag)
			{
				query.append(" and ( hn.rule_1 LIKE :RULE or hn.rule_2 LIKE :RULE or hn.rule_3 LIKE :RULE or hn.rule_4  LIKE :RULE)   ");
				paramMap.put("RULE", "%"+searchRequest.getStatueSearchRule().trim()+"%");
			}
			else
			{
				query.append("  ( hn.rule_1 LIKE :RULE or hn.rule_2 LIKE :RULE or hn.rule_3 LIKE :RULE or hn.rule_4  LIKE :RULE )   ");
				paramMap.put("RULE", "%"+searchRequest.getStatueSearchRule().trim()+"%");
			}
			flag = true;
		}
		
		if (searchRequest.getStatueSearchArticle()!=null)
		{
			if(flag)
			{
				query.append(" and ( hn.article_1 LIKE :ARTICLE or hn.article_2 LIKE :ARTICLE or hn.article_3 LIKE :ARTICLE or hn.article_4  LIKE :ARTICLE)   ");
				paramMap.put("ARTICLE", "%"+searchRequest.getStatueSearchArticle().trim()+"%");
			}
			else
			{
				query.append("  ( hn.article_1 LIKE :ARTICLE or hn.article_2 LIKE :ARTICLE or hn.article_3 LIKE :ARTICLE or hn.article_4  LIKE :ARTICLE )   ");
				paramMap.put("ARTICLE", "%"+searchRequest.getStatueSearchArticle().trim()+"%");
			}
			flag = true;
		}
		
		
	
		
		if(flag)
		{
		query.append("  and ce.is_active =1 and ce.is_live =1  ");
		}
		else
		{
			query.append(" ce.is_active =1 and ce.is_live =1  ");
		}
		
		/*query.append(" )A ORDER BY A.DATE_1 ");*/
		
		if (searchRequest.getDecidedDateOrder() != null) {

			if (searchRequest.getDecidedDateOrder().equals("a")) {
				query.append("  )A ORDER BY A.DATE_1 ");
			} else {
				query.append("  )A ORDER BY A.DATE_1 desc");
			}
		}

		else if (searchRequest.getAppellantNameOrder() != null) {

			if (searchRequest.getAppellantNameOrder().equals("a")) {
				query.append("  )A ORDER BY A.appellant_1  ");
			} else {
				query.append("  )A ORDER BY A.appellant_1 desc");
			}
		}

		else {
			query.append("  )A ORDER BY A.DATE_1 ");
		}

		
		
		int startRow = pageNumber==0 ? 0 : pageNumber*8;
		int endRow = 8;
		
		if(!forSize)
		{
		
		query.append(" LIMIT :START_ROW , :END_ROW ");
		paramMap.put("START_ROW",startRow);
		paramMap.put("END_ROW",endRow);
		}
		Query qry = entityManager.createNativeQuery(query.toString());
		setQueryParameter(qry, paramMap);
		
		List ls = qry.getResultList();
		
		return ls;
	}

	
	public List<Object> performDashBoardSearch2(List idListForSize,int pageNumber,int perPage){
		
		pageNumber = pageNumber*8;
	  if(pageNumber>idListForSize.size()){
		  return new ArrayList();
	  }
		int end = 0;
		if(idListForSize.size()<pageNumber+8){
			end = idListForSize.size();
		}else {
			end = pageNumber+8;
		}
		return idListForSize.subList(pageNumber,end);
		
	}
	@Override
	public List<Object> performDashBoardSearch(SearchRequest searchRequest, int pageNumber, int perPage,
			boolean forSize) {
		
		  StringBuffer query = new StringBuffer();
			
		  Map<String, Object> paramMap = new HashMap<String, Object>();
			
			String searchValue = searchRequest.getDashboardSearchValue();
			
			try{
				int integerSearchValue = Integer.parseInt(searchValue.trim());
				
				query.append(" SELECT DISTINCT A.ID FROM "
						+ "  ( SELECT ce.id,ce.appellant as appellant_1, "
						+ "    DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
						+ "    FROM case_entity ce left join case_history ch on ce.case_history_entity_id =  ch.id "
						+ "    where ce.doc_id = :SEARCHVALUE  "
						+ "    and ce.is_active = 1"
						+ "    and ce.is_live = 1  ");
						
				paramMap.put("SEARCHVALUE",integerSearchValue );
				
				if (searchRequest.getStartsWithAlphabet() != null)
				{
					
						query.append(" and ce.appellant LIKE :STARTALBHABET ");
						paramMap.put("STARTALBHABET", searchRequest.getStartsWithAlphabet().trim()+"%");

				}
				
			if (searchRequest.getDecidedDateOrder() != null) {

				if (searchRequest.getDecidedDateOrder().equals("a")) {
					query.append("  )A ORDER BY A.DATE_1 ");
				} else {
					query.append("  )A ORDER BY A.DATE_1 desc");
				}
			}

			else if (searchRequest.getAppellantNameOrder() != null) {

				if (searchRequest.getAppellantNameOrder().equals("a")) {
					query.append("  )A ORDER BY A.appellant_1  ");
				} else {
					query.append("  )A ORDER BY A.appellant_1 desc");
				}
			}

			else {
				query.append("  )A ORDER BY A.DATE_1 ");
			}
				
			
		
			}
			catch(Exception ex){
			
				query.append(" SELECT DISTINCT A.ID FROM "
						+ "  ( SELECT ce.id,ce.appellant as appellant_1,  "
						+ "    DATE(CONCAT_WS('-', ch.decided_year, ch.decided_month, ch.decided_day)) as date_1 "
						+ "    FROM case_entity ce left join case_history ch on ce.case_history_entity_id =  ch.id "
						+ "    left join head_note hn on ce.id = hn.case_entity_id "
						+ "    where ( upper(hn.topic) like :SEARCHVALUE or upper(ce.appellant) like :SEARCHVALUE or upper(ce.respondent) like :SEARCHVALUE)  "
						+ "    and ce.is_active = 1"
						+ "    and ce.is_live = 1  ");
						
				paramMap.put("SEARCHVALUE","%"+searchValue.trim()+"%" );
						
						if (searchRequest.getStartsWithAlphabet() != null)
						{
							
								query.append(" and ce.appellant LIKE :STARTALBHABET ");
								paramMap.put("STARTALBHABET", searchRequest.getStartsWithAlphabet().trim()+"%");

						}
						
						
						if (searchRequest.getDecidedDateOrder() != null) {

							if (searchRequest.getDecidedDateOrder().equals("a")) {
								query.append("  )A ORDER BY A.DATE_1 ");
							} else {
								query.append("  )A ORDER BY A.DATE_1 desc");
							}
						}

						else if (searchRequest.getAppellantNameOrder() != null) {

							if (searchRequest.getAppellantNameOrder().equals("a")) {
								query.append("  )A ORDER BY A.appellant_1  ");
							} else {
								query.append("  )A ORDER BY A.appellant_1 desc");
							}
						}

						else {
							query.append("  )A ORDER BY A.DATE_1 ");
						}
			}
			
			
		
			int startRow = pageNumber==0 ? 0 : pageNumber*8;
			int endRow = 8;
			
			if(!forSize)
			{
			
			
			query.append(" LIMIT :START_ROW , :END_ROW ");
			paramMap.put("START_ROW",startRow);
			paramMap.put("END_ROW",endRow);
			}
			Query qry = entityManager.createNativeQuery(query.toString());
			setQueryParameter(qry, paramMap);
			
			List ls = qry.getResultList();
			
			return ls;
			
		
	}

}
