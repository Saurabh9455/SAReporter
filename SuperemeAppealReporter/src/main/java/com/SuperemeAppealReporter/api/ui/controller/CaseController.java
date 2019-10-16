package com.SuperemeAppealReporter.api.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.converter.CaseConverter;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
public class CaseController {

	
	@Autowired
	CaseService caseService;
	
	
/****************************************Add Case handler method*****************************************/
	public ResponseEntity<BaseApiResponse> addCaseHandler(@RequestBody AddCaseRequest addCaseRequest)
	{
		/**converting request to bo**/
		AddCaseBo addCaseBo = CaseConverter.convertAddCaseRequestToAddCaseBo(addCaseRequest);
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.addCaseService(addCaseBo);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
}
