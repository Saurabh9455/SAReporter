package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

public interface SearchService {

	//public CommonPaginationResponse mainSearchService();
	public CommonPaginationResponse searchService(SearchRequest searchRequest,int pageNumber, int perPage);
	
}
