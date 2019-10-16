package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseRequest;

public class CaseConverter {

	
	public static AddCaseBo convertAddCaseRequestToAddCaseBo(AddCaseRequest addCaseRequest)
	{
		AddCaseBo addCaseBo = new AddCaseBo();
		BeanUtils.copyProperties(addCaseRequest, addCaseBo);
		return addCaseBo;
	}
}
