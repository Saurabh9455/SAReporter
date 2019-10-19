package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadPdfRequest;

public class CaseConverter {

	
	public static AddCaseBo convertAddCaseRequestToAddCaseBo(AddCaseRequest addCaseRequest)
	{
		AddCaseBo addCaseBo = new AddCaseBo();
		BeanUtils.copyProperties(addCaseRequest, addCaseBo);
		return addCaseBo;
	}
	
	public static UploadCasePdfBo convertUploadPdfRequestToUploadCasePdfBo(UploadPdfRequest uploadPdfRequest)
	{
		UploadCasePdfBo uploadCasePdfBo = new UploadCasePdfBo();
		BeanUtils.copyProperties(uploadPdfRequest, uploadCasePdfBo);
		return uploadCasePdfBo;
	}
}
