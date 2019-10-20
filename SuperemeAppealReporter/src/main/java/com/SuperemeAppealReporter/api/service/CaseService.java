package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.GetCaseListBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;


public interface CaseService {

	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo);
	
	public CommonMessageResponse uploadCasePdf(UploadCasePdfBo uploadCasePdfBo,int docId);
	
	public CommonPaginationResponse getCaseList(GetCaseListBo getCaseListBo,int pageNumber, int perPage);
}
