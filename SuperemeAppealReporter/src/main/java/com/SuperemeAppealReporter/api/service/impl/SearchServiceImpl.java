package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
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
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPage);
		Page<Object> page = null;
		
		/**for DashBoardSearchTyPE***************************************/
		if(searchType.equalsIgnoreCase("DashBoardSearchType"))
		{
			
			String dashboardSearchValue = searchRequest.getDashboardSearchValue();
			try
			{
			
				long docIdForSearch = Integer.parseInt(dashboardSearchValue.trim());
				page = caseRepository.dashboardSearchForDocIdV2(pageableRequest, docIdForSearch);
			}
			catch(NumberFormatException ex)
			{
				page=	caseRepository.dashboardSearchV2(pageableRequest,dashboardSearchValue.toUpperCase().trim());
			}
			
					List idList = page.getContent();
					System.out.println("-----The list size is--->"+idList.size());
					System.out.println(idList);
					Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

					searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
					commonPaginationResponse = new CommonPaginationResponse();

					commonPaginationResponse.setOjectList(searchCaseListResponseSet);
					commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(page.getTotalPages());
					return commonPaginationResponse;
	
		}
		
		else if(searchType.equalsIgnoreCase("CitationSearchType"))
		{
			List idListForSize = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,true);
			List idList = searchDao.performCitationSearch(searchRequest, pageNumber, perPage,false);
			Set<SearchCaseListResponse> searchCaseListResponseSet = new HashSet<SearchCaseListResponse>();

			searchCaseListResponseSet = prepareCaseRepresentation(idList,pageNumber+1);
			commonPaginationResponse = new CommonPaginationResponse();

			commonPaginationResponse.setOjectList(searchCaseListResponseSet);
			commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit((int)(Math.ceil(idListForSize.size()/8.0)));
			return commonPaginationResponse;
					
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
					List idList = searchDao.performMainSearch(searchRequest, pageNumber, perPage,false);
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
	
	private Set<SearchCaseListResponse>  prepareCaseRepresentation(List caseIdList,int pageNumber)
	{
		pageNumber = pageNumber-1;
		int startNumber = pageNumber*8;
		List<Integer> caseIdListV2 = new ArrayList<Integer>();
		for(Object obj : caseIdList)
		{
			caseIdListV2.add(Integer.parseInt(String.valueOf(obj)));
			
		}
		List<CaseEntity> caseEntities = null;
		if(caseIdListV2!=null && !caseIdListV2.isEmpty())
		{
		caseEntities= caseRepository.findAppResByIdV2(caseIdListV2);
		}
		else
		{
			caseEntities = new ArrayList<CaseEntity>();
		}
		Set<SearchCaseListResponse> searchCaseListResponses = new LinkedHashSet<SearchCaseListResponse>();
		
		
		for(CaseEntity entity : caseEntities)
		{
	   ++startNumber;
		String appellant = entity.getAppellant();
		String respondent = entity.getRespondent();
		String docId = entity.getDocId()+"";
		SearchCaseListResponse searchCaseListResponse = new SearchCaseListResponse();
	
		
	
		String courtType = entity.getCourtDetailEntity().getCourtEntity().getCourtType();
		
		CaseHistoryEntity caseHisotry = entity.getCaseHistoryEntity();
		String decidedDate = caseHisotry.getDecided_day()+"/"+caseHisotry.getDecidedMonth()+"/"+caseHisotry.getDecidedYear();
		
		String courtShortForm = prepareCourtShortForm(courtType);
		
		
		List<HeadnoteEntity> headnoteEntities = entity.getHeadNoteEntitySet();
		
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
			searchCaseListResponse.setStartNumber(startNumber);
	
		
		searchCaseListResponses.add(searchCaseListResponse);
		
		 
		
	}
		return searchCaseListResponses;
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

}
