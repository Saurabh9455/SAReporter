package com.SuperemeAppealReporter.api.ui.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.io.entity.DocIdGenerator;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCommonMasterDataResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path = RestMappingConstant.Master.MASTER_BASE_URI)
public class MasterController {
	
	
	
	@Autowired
	MasterService masterService;
	
	/****************************************Get role master data handler method*****************************************/
	@GetMapping(path = RestMappingConstant.Master.GET_ROLE_MASTER_DATA_URI)
	public ResponseEntity<BaseApiResponse> getRoleMasterData()
	{
		
		/**calling service layer**/
		GetCommonMasterDataResponse getCommonMasterDataResponse = masterService.getRoleMasterData();
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	/****************************************Get country master data handler method*****************************************/
	@GetMapping(path = RestMappingConstant.Master.GET_COUNTRY_MASTER_DATA_URI)
	public ResponseEntity<BaseApiResponse> getCountryMasterData()
	{
		
		/**calling service layer**/
		GetCommonMasterDataResponse getCommonMasterDataResponse = masterService.getCountryMasterData();
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	
	
	/****************************************Get state master data handler method*****************************************/
	@GetMapping(path = RestMappingConstant.Master.GET_STATE_MASTER_DATA_URI)
	public ResponseEntity<BaseApiResponse> getStateMasterData(@PathVariable("countryId") int countryId)
	{
		
		/**calling service layer**/
		GetCommonMasterDataResponse getCommonMasterDataResponse = masterService.getStateMasterDataResponse(countryId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}

	/****************************************Get city master data handler method*****************************************/
	@GetMapping(path = RestMappingConstant.Master.GET_CITY_MASTER_DATA_URI)
	public ResponseEntity<BaseApiResponse> getCityMasterData(@PathVariable("stateId") int stateId)
	{
		
		/**calling service layer**/
		GetCommonMasterDataResponse getCommonMasterDataResponse = masterService.getCityMasterDataResponse(stateId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	
	
	
	/****************************************Get city master data handler method*****************************************/
	@GetMapping(path = RestMappingConstant.Master.GET_NEXT_DOC_ID_URI)
	public ResponseEntity<BaseApiResponse> getNextDocId()
	{
		
		/**calling service layer**/
		int nextDocId = masterService.getNextDocId();
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(nextDocId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCommonMasterDataResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
}
