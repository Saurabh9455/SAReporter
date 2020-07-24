package com.SuperemeAppealReporter.api.io.dao;

import java.util.List;

import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;

public interface SearchDao {

	public List<Object> performDashBoardSearch2(List idListForSize,int pageNumber,int perPage);
		
	public List<Object> performMainSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize);
	
	public List<Object> performCitationSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize,boolean isOtherCitation);
	
	public List<Object> performStatueSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize);

    public List<Object> performDashBoardSearch(SearchRequest searchRequest, int pageNumber, int perPage, boolean forSize);
}
