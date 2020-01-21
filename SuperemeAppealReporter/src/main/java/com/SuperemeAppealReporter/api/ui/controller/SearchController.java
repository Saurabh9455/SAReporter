package com.SuperemeAppealReporter.api.ui.controller;

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
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
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
		
	CommonPaginationResponse commonPaginationResponse = searchService.searchService(searchRequest, pageNumber, perPage);
	/**returning get role master data response**/
	BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
	return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
}
