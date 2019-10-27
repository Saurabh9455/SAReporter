package com.SuperemeAppealReporter.api.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.GetCaseListBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;


public interface CaseService {

	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo);
	
	public CommonMessageResponse editCaseService(AddCaseBo addCaseBo);
	
	public CommonMessageResponse uploadCasePdf(UploadCasePdfBo uploadCasePdfBo,Long docId);
	
	public CommonPaginationResponse getCaseList(GetCaseListBo getCaseListBo,int pageNumber, int perPage);

    public Resource getCasePdf(long docId);

    public CommonMessageResponse deleteCaseService( int docId);

}
