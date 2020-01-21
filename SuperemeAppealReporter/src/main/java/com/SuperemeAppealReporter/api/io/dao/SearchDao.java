package com.SuperemeAppealReporter.api.io.dao;

import java.util.List;

import com.SuperemeAppealReporter.api.ui.model.request.SearchRequest;

public interface SearchDao {

	public List<Object> performMainSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize);
	
	public List<Object> performCitationSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize);
	
	public List<Object> performStatueSearch(SearchRequest searchRequest,int pageNumber,int perPage,boolean forSize);
}
