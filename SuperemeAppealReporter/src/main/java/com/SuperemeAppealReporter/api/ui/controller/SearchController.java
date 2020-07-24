package com.SuperemeAppealReporter.api.ui.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.service.SearchService;
import com.SuperemeAppealReporter.api.ui.model.request.ActNameMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.TopicMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCommonMasterDataResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCourtDropDownResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path=RestMappingConstant.User.USER_BASE_URI)
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@PostMapping(path = RestMappingConstant.Search.DASHBOARD_SEARCH_URI)
	public ResponseEntity<BaseApiResponse> dashboardSearchHandler(@RequestBody SearchRequest searchRequest,@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "65535") int perPage)
	{
	
		CommonPaginationResponse commonPaginationResponse = null;
	
		
	commonPaginationResponse = searchService.searchService(searchRequest, pageNumber, perPage);
	
	/**returning get role master data response**/
	BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
	return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	@PostMapping(path = RestMappingConstant.Search.ACT_NAME_MASTER_SEARCH)
	public ResponseEntity<BaseApiResponse> actNameMasterSearchHandler(@RequestBody ActNameMasterSearchRequest searchRequest){
		
		HashSet<String> actNames = searchService.performActNameMasterSearch(searchRequest);
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse(); 
List<GetCourtDropDownResponse> dropDownResponseList = new ArrayList<GetCourtDropDownResponse>();
		
		for(String out : actNames){
			GetCourtDropDownResponse getCourtDropDownResponse = new GetCourtDropDownResponse();
			getCourtDropDownResponse.setId(0);
			getCourtDropDownResponse.setLabel(out);
			getCourtDropDownResponse.setValue("0");
			dropDownResponseList.add(getCourtDropDownResponse);
		}
		
		getCommonMasterDataResponse.setObjectList(dropDownResponseList);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	@PostMapping(path = RestMappingConstant.Search.TOPIC_MASTER_SEARCH)
	public ResponseEntity<BaseApiResponse> topicMasterSearchHandler(@RequestBody TopicMasterSearchRequest searchRequest){
		
		HashSet<String> topicNames = searchService.performTopicMasterSearch(searchRequest);
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse(); 
List<GetCourtDropDownResponse> dropDownResponseList = new ArrayList<GetCourtDropDownResponse>();
		
		for(String out : topicNames){
			GetCourtDropDownResponse getCourtDropDownResponse = new GetCourtDropDownResponse();
			getCourtDropDownResponse.setId(0);
			getCourtDropDownResponse.setLabel(out);
			getCourtDropDownResponse.setValue("0");
			dropDownResponseList.add(getCourtDropDownResponse);
		}
		
		getCommonMasterDataResponse.setObjectList(dropDownResponseList);

		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
}
