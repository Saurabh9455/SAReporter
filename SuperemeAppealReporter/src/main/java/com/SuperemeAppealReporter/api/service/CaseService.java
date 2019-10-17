package com.SuperemeAppealReporter.api.service;

import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

@Service
public interface CaseService {

	public CommonMessageResponse addCaseService(AddCaseBo addCaseBo);
}
