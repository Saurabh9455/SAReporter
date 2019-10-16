package com.SuperemeAppealReporter.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.io.entity.DocIdGenerator;
import com.SuperemeAppealReporter.api.io.repository.CitationCategoryRepository;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.ui.model.request.CitationRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

@Service
public class CaseServiceImpl implements CaseService{

	@Autowired
	private CitationCategoryRepository citationCategoryRepository;
	
	@Autowired
	private MasterService masterService;
	@Override
	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo) {

		/**fetching doc id**/
		int docId = (int)addCaseBo.getDocId();
		
		/**to get the next doc Id as incremented one**/
		DocIdGenerator docIdGenerator = new DocIdGenerator();
		masterService.save(docIdGenerator);
		
		
		/**Creating CitationEntity**/
		CitationRequest citationRequest = addCaseBo.getCitationRequest();
		int citationYear = citationRequest.getYear();
		int citationPageNumber = citationRequest.getPageNumber();
		String otherCitation = citationRequest.getOtherCitation();
		
		return null;
	}

	
}
