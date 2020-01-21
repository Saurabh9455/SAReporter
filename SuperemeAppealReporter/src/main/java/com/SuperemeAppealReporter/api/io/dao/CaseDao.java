package com.SuperemeAppealReporter.api.io.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SuperemeAppealReporter.api.io.entity.CaseEntity;

public interface CaseDao {

	public Page<Object> getCasePage(Pageable pageable, List<String> courtCtegoryList,
			List<String> caseCategoryList, List<Boolean> liveList, List<Boolean> overRuledList );
	
	public Page<Object> getCasePage(Pageable pageable, List<String> courtCtegoryList,
			List<String> caseCategoryList, List<Boolean> liveList, List<Boolean> overRuledList,String searchValue );
	
	public Page<Object> getCasePageInt(Pageable pageable, List<String> courtCtegoryList,
			List<String> caseCategoryList, List<Boolean> liveList, List<Boolean> overRuledList,Long searchValue );

	public String getPdfPathByDocId(long docId);
	
	public CaseEntity getSingleCaseByDocId(int docId);

}
