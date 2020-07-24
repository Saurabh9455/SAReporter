package com.SuperemeAppealReporter.api.service;

import java.util.HashSet;

import com.SuperemeAppealReporter.api.ui.model.request.ActNameMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.TopicMasterSearchRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

public interface SearchService {

	//public CommonPaginationResponse mainSearchService();
	public CommonPaginationResponse searchService(SearchRequest searchRequest,int pageNumber, int perPage);

	public HashSet<String> performActNameMasterSearch(ActNameMasterSearchRequest searchRequest);
	
	public HashSet<String> performTopicMasterSearch(TopicMasterSearchRequest searchRequest);
	
}
