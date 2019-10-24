package com.SuperemeAppealReporter.api.io.dao.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.io.dao.CaseDao;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;

@Component
public class CaseDaoImpl implements CaseDao {

	
	
	@Autowired
	CaseRepository caseRepository;

	@Override
	public Page<CaseEntity> getCasePage(Pageable pageable, List<String> courtCategoryList, List<String> caseCategoryList,
			List<Boolean> liveList, List<Boolean> overuledList) {
	
		return caseRepository.getCaseList(pageable, courtCategoryList, caseCategoryList, liveList, overuledList);
	}
	
	
	@Override
	public Page<CaseEntity> getCasePage(Pageable pageable, List<String> courtCategoryList, List<String> caseCategoryList,
			List<Boolean> liveList, List<Boolean> overuledList,String searchValue) {
	
		return caseRepository.getCaseList(pageable, courtCategoryList, caseCategoryList, liveList, overuledList,searchValue);
	}


	@Override
	public Page<CaseEntity> getCasePageInt(Pageable pageable, List<String> courtCtegoryList,
			List<String> caseCategoryList, List<Boolean> liveList, List<Boolean> overRuledList, Long searchValue) {
		return caseRepository.getCaseListInt(pageable, courtCtegoryList, caseCategoryList, liveList, overRuledList,searchValue);
	}


	@Override
	public String getPdfPathByDocId(long docId) {
		
		return caseRepository.getPdfPathByDocId(docId);
	}
	
	

}
