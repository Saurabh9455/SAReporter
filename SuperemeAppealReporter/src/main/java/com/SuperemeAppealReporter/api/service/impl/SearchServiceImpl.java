package com.SuperemeAppealReporter.api.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.SearchDao;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.entity.CaseHistoryEntity;
import com.SuperemeAppealReporter.api.io.entity.HeadnoteEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.CourtRepository;
import com.SuperemeAppealReporter.api.io.repository.HeadnoteRepository;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.service.SearchService;
import com.SuperemeAppealReporter.api.shared.util.AppUtility;
import com.SuperemeAppealReporter.api.ui.model.request.ActNameMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.TopicMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.SearchCaseListResponse;

@Service
@SuppressWarnings("rawtypes")
public class SearchServiceImpl implements SearchService {

	@Autowired
	CaseRepository caseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SearchDao searchDao;
	
	@Autowired
	CourtRepository courtRepository;
	
	@Autowired
	HeadnoteRepository headnoteRepository;
	
	
	@Override
	public CommonPaginationResponse searchService(SearchRequest searchRequest,int pageNumber,int perPage) {
		
		
		CommonPaginationResponse commonPaginationResponse = null;
		
		try
		{
		/**Checking whether the user has an active plan**/
		boolean isSubscriptionActive = isUserHasAnActivePlan();
		
		
		if(isSubscriptionActive)
		{
		/**fetching searchType**/
		String searchType = searchRequest.getSearchType();
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		
		perPage = 8;
				
		
		/**for DashBoardSearchTyPE***************************************/
		if(searchType.equalsIgnoreCase("DashBoardSearchType"))
		{
			
		
			List idListForSize = searchDao.performDashBoardSearch(searchRequest, pageNumber, perPage,true);
			List idList = searchDao.performDashBoardSearch(searchRequest, pageNumber, perPage,false);
			//List idList = searchDao.performDashBoardSearch2(idListForSize, pageNumber, perPage);
			Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

			searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
			commonPaginationResponse = new CommonPaginationResponse();

			commonPaginationResponse.setOjectList(searchCaseListResponseSet);
			commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
			return commonPaginationResponse;
			
	
		}
		
		else if(searchType.equalsIgnoreCase("CitationSearchType"))
		{
			
			/**For Other Citation Search**/
			if(searchRequest.isOtherSearch()){
				List idListForSize = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,true,true);
				List idList = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,false,true);
				Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

				searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
				commonPaginationResponse = new CommonPaginationResponse();

				commonPaginationResponse.setOjectList(searchCaseListResponseSet);
				commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
				return commonPaginationResponse;
		
			}
			
			/**For Main Citation Search**/
			else {
				
				
				List idListForSize = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,true,false);
				List idList = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,false,false);
				Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

				searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
				commonPaginationResponse = new CommonPaginationResponse();

				commonPaginationResponse.setOjectList(searchCaseListResponseSet);
				commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
				return commonPaginationResponse;
			}
					
		}
		
		else if(searchType.equalsIgnoreCase("StatueSearch"))
		{
			List idListForSize = searchDao.performStatueSearch(searchRequest, pageNumber, perPage,true);
			List idList = searchDao.performStatueSearch(searchRequest, pageNumber, perPage,false);
			Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

			searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
			commonPaginationResponse = new CommonPaginationResponse();

			commonPaginationResponse.setOjectList(searchCaseListResponseSet);
			commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
			
			return commonPaginationResponse;
		}
		
		else if(searchType.equalsIgnoreCase("MainSearchType"))
		{

			        List idListForSize = searchDao.performMainSearch(searchRequest, pageNumber, perPage,true);
			    	/*int startRow = pageNumber==0 ? 0 : pageNumber*8;
			    	int endRow = 8;*/
					List idList = searchDao.performMainSearch(searchRequest, pageNumber, perPage,false);
			    	//List idList = idListForSize.subList(startRow, startRow+8);
					Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

					searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
					commonPaginationResponse = new CommonPaginationResponse();

					commonPaginationResponse.setOjectList(searchCaseListResponseSet);
					commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
					
					return commonPaginationResponse;
			
		}
       
        
     
		}
		else
		{
			throw new AppException(ErrorConstant.SearchCaseError.ERROR_TYPE,
					ErrorConstant.SearchCaseError.ERROR_CODE, ErrorConstant.SearchCaseError.ERROR_MESSAGE
					);
		}
        return commonPaginationResponse;
		}
		
		catch(AppException ex)
		{
			throw ex;
		}
	
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in SearchServiceImpl --> searchService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
		}
	}
	
	public boolean isUserHasAnActivePlan()
	{
		     String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			 UserEntity userEntity = userRepository.getUserEntityByEmail(userEmail);
			 return userEntity.isSubscriptionActive();
			 
			 
	}
	
	public Set<SearchCaseListResponse>  prepareCaseRepresentation(List caseIdList,int pageNumber) throws ParseException
	{
		pageNumber = pageNumber-1;
		int startNumber = pageNumber*8;
		List<Integer> caseIdListV2 = new ArrayList<Integer>();
		for(Object obj : caseIdList)
		{
			caseIdListV2.add(Integer.parseInt(String.valueOf(obj)));
			
		}
		List<CaseEntity> caseEntities  = new ArrayList<CaseEntity>();
		
		/*if(caseIdListV2!=null && !caseIdListV2.isEmpty())
		{
		   for(Integer k :caseIdListV2 ){
			CaseEntity cE = caseRepository.findById(k).get();

			String otherCitation = cE.getCitationEntity().getOtherCitation();
			if(StringUtils.isNotBlank(otherCitation)){
				
			}
		   caseEntities.add(cE);
		   }
		}*/
		List<Object[]> cE = new ArrayList<Object[]>();
		if(caseIdListV2!=null && !caseIdListV2.isEmpty())
		{
		  
		//	cE = caseRepository.findAllById(caseIdListV2).iterator();
			cE=  caseRepository.getCaseEntityFieldsForPreparation(caseIdListV2);

		}
		
		
		
		System.out.println("============Size of the Id List ==========="+caseIdListV2.size());
		
		System.out.println("============Size of the Case Entity List==========="+caseEntities.size());
		
		Set<SearchCaseListResponse> searchCaseListResponses = new LinkedHashSet<SearchCaseListResponse>();
		//TreeMap<Date,SearchCaseListResponse> decidedDateAndRestRepresentationMap = new TreeMap<Date,SearchCaseListResponse>();
		List<SearchCaseListResponse> decidedDateAndRestRepresentationSet = new ArrayList<SearchCaseListResponse>();
		List<SearchCaseListResponse> searchCaseListResponsList = new ArrayList<SearchCaseListResponse>();
		ArrayList<Date> decidedDateList = new ArrayList<Date>();
		int secondsForKeepingKeyUnique = 0;
		for(Object[] obj : cE)
		{
		//	CaseEntity entity  = cE.next();
			++secondsForKeepingKeyUnique;
		String appellant = String.valueOf(obj[1]);
		String respondent = String.valueOf(obj[2]);
		String docId = String.valueOf(obj[0]);
		SearchCaseListResponse searchCaseListResponse = new SearchCaseListResponse();
	/*
	 * select cE.docId, cE.appellant, cE.respondent, 
	 * cE.courtDetailEntity.courtEntity.courtType, 
	 * cE.caseHistoryEntity.decided_day, 
	 * cE.caseHistoryEntity.decidedMonth, 
	 * cE.caseHistoryEntity.decidedYear
	 *  where cE.id in () 
	 * 
	 */
		
	
		String courtType =String.valueOf(obj[3]);;
		
		//CaseHistoryEntity caseHisotry = entity.getCaseHistoryEntity();
		String decidedDate = String.valueOf(obj[4])+"/"+String.valueOf(obj[5])+"/"+String.valueOf(obj[6]);;
		
		String courtShortForm = prepareCourtShortForm(courtType);
		
		
		List<HeadnoteEntity> headnoteEntities = headnoteRepository.getRecord(Integer.parseInt(String.valueOf(obj[7])));
		
		String headNote = "";
		boolean flag = false;
		
		for(HeadnoteEntity entity2 : headnoteEntities)
		{
			if(flag)
			{
				headNote = headNote + " with " ;
			}
			
			if(entity2.getActname1()!=null &&  !entity2.getActname1().equalsIgnoreCase("none"))
			{
				headNote = headNote + entity2.getActname1();
				flag = true;
			}
			if(entity2.getSection1()!=null &&  !entity2.getSection1().equalsIgnoreCase("none"))
			{
				if(headNote.isEmpty())
				{
					headNote = entity2.getSection1();
				}
				else
				{
				headNote = headNote+ " — "+entity2.getSection1();
				}
				flag = true;
			}
			
			if(entity2.getActname2()!=null &&  !entity2.getActname2().equalsIgnoreCase("none"))
			{
				if(headNote.isEmpty())
				{
					headNote = entity2.getActname2();
				}
				else
				{
				headNote = headNote+ " and "+entity2.getActname2();
				}
				flag = true;
			}
			if(entity2.getSection2()!=null &&  !entity2.getSection2().equalsIgnoreCase("none"))
			{
				
				if(headNote.isEmpty())
				{
					headNote = entity2.getSection2();
				}
				else
				{
				headNote = headNote+ " — "+entity2.getSection2();
				}
				flag = true;
			}
			if(entity2.getActname3()!=null &&  !entity2.getActname3().equalsIgnoreCase("none"))
			{
				if(headNote.isEmpty())
				{
					headNote = entity2.getActname3();
				}
				else
				{
				headNote =  headNote + " — "+entity2.getActname3();
				}
				flag = true;
			}
			if(entity2.getSection3()!=null &&  !entity2.getSection3().equalsIgnoreCase("none"))
			{
				if(headNote.isEmpty())
				{
					headNote = entity2.getSection3();
				}
				else
				{
				headNote = headNote+ " — "+entity2.getSection3();
				}
				flag = true;
			}
			if(entity2.getTopic()!=null && !entity2.getTopic().equalsIgnoreCase("none"))
			{
				if(headNote.isEmpty())
				{
					headNote = entity2.getTopic();
				}
				else
				{
				headNote = headNote+ " — "+entity2.getTopic();
				}
				flag = true;
			}
			
		}
		
		
			searchCaseListResponse.setDocId(docId);
			
			searchCaseListResponse.setCaseRepresentation(appellant+" VS "+respondent+" ( "+courtShortForm+" ) "+decidedDate+"# "+headNote);
			
			searchCaseListResponsList.add(searchCaseListResponse);
			
			Date decidedDateKey = AppUtility.convertStringToDate("dd/MM/yyyy", decidedDate);
			
			decidedDateList.add(decidedDateKey);
			
			decidedDateAndRestRepresentationSet.add(searchCaseListResponse);
			
			/*   SearchCaseListResponse prevSearchCaseListResponse =	decidedDateAndRestRepresentationMap.put(decidedDateKey,searchCaseListResponse );
			if(prevSearchCaseListResponse != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(decidedDateKey);
				cal.add(Calendar.SECOND, secondsForKeepingKeyUnique);
				
				prevSearchCaseListResponse =	decidedDateAndRestRepresentationMap.put(cal.getTime(),prevSearchCaseListResponse );
			}
		*/
		
		 
		
	}
		for(SearchCaseListResponse searchCaseListResponse :decidedDateAndRestRepresentationSet  ){
			   ++startNumber;
			 
			 searchCaseListResponse.setStartNumber(startNumber);
			searchCaseListResponses.add(searchCaseListResponse);
		}
		return searchCaseListResponses;
		
		/*Collections.sort(decidedDateList);
		for(int i=0;i<decidedDateList.size();i++){
			
			for(int j=0;j<searchCaseListResponsList.size();j++){
				
				String decidedDateString = AppUtility.convertDateToString("dd/MM/yyyy", decidedDateList.get(i))	;
				
				SearchCaseListResponse res = searchCaseListResponsList.get(j);
				
				if(res.getCaseRepresentation().contains(decidedDateString)){
				   
					startNumber++;
					res.setStartNumber(startNumber);
					searchCaseListResponses.add(res);
				}
				
			}
		}*/
		/*return searchCaseListResponses;*/
	}
	
	private String prepareCourtShortForm(String courtType)
	{
		String arr[] = courtType.split("\\s+");
		String result = "";
	    for(String out : arr)
	    {
	    	result  = result+Character.toUpperCase(out.charAt(0));
	    }
	    return result;	
	}

	@Override
	public HashSet<String> performActNameMasterSearch(ActNameMasterSearchRequest searchRequest) {
		
		HashSet<String> actNameList = new HashSet<String>();
		System.out.println("----isLive----"+headnoteRepository.findById(260).get().getCaseEntity().isLive());
		if(StringUtils.isNotBlank(searchRequest.getSearchKeywords())){
			Pageable pageableRequest = PageRequest.of(0, 100);
		Page<String[]> stringArrListPage = headnoteRepository.searchActname(searchRequest.getSearchKeywords().toUpperCase(),pageableRequest);
		
		List<String[]> stringArrList = stringArrListPage.getContent();
		System.out.println("------------stringArrList---------"+stringArrList.size());
		if(stringArrList!=null &&  !stringArrList.isEmpty()){
		 
			for(Object[] obj : stringArrList ) {

					String headNoteArr[] = new String[] { String.valueOf(obj[0]), String.valueOf(obj[1]),
							String.valueOf(obj[2]), String.valueOf(obj[3]) };
				
				for(String out : headNoteArr){
					
					if(actNameList.size()>50){
						break;
					}
					if(StringUtils.isNotBlank(out)){
						
						if(out.toUpperCase().contains(searchRequest.getSearchKeywords().toUpperCase())){
							System.out.println("------OUT Value----"+out);
						actNameList.add(out.trim());
						}
					}
				}
				
				
				
			}
		}
		
		
		}
		return actNameList;
	}
	
	
	@Override
	public HashSet<String> performTopicMasterSearch(TopicMasterSearchRequest searchRequest) {
		
		HashSet<String> topicList = new HashSet<String>();
		//System.out.println("----isLive----"+headnoteRepository.findById(260).get().getCaseEntity().isLive());
		if(StringUtils.isNotBlank(searchRequest.getSearchKeywords())){
			Pageable pageableRequest = PageRequest.of(0, 100);
		Page<String> stringArrListPage = headnoteRepository.searchTopic(searchRequest.getSearchKeywords().toUpperCase(),pageableRequest);
		
		List<String> stringArrList = stringArrListPage.getContent();
		System.out.println("------------stringArrList---------"+stringArrList.size());
		if(stringArrList!=null &&  !stringArrList.isEmpty()){
		 
			for(String out : stringArrList ) {

					
					if(topicList.size()>50){
						break;
					}
					if(StringUtils.isNotBlank(out)){
						
						if(out.toUpperCase().contains(searchRequest.getSearchKeywords().toUpperCase())){
							System.out.println("------OUT Value----"+out);
							topicList.add(out.trim());
						}
					}
				}
				
				
				
			}
		}
		
		
		
		return topicList;
	}

}
