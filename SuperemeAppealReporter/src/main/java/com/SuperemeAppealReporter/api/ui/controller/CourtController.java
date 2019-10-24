package com.SuperemeAppealReporter.api.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.bo.AddCourtBo;
import com.SuperemeAppealReporter.api.bo.AddCourtBranchBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBranchBo;
import com.SuperemeAppealReporter.api.bo.GetCourtBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.converter.CourtConverter;
import com.SuperemeAppealReporter.api.service.CourtService;
import com.SuperemeAppealReporter.api.ui.model.request.AddCourtBranchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.AddCourtRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteCourtBranchRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteCourtRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetCourtRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path =RestMappingConstant.Admin.ADMIN_BASE_URI)
public class CourtController {

	@Autowired CourtService courtService;
	
	/******************************Add Court Request********************************/
	@PostMapping(path = RestMappingConstant.Court.ADD_COURT_URI)
	public ResponseEntity<BaseApiResponse> addCourt(@Valid @RequestBody AddCourtRequest addCourtRequest){
		/******************************Converting Request into Bo********************************/
		AddCourtBo addCourtBo = CourtConverter.convertAddCourtRequestToAddCourtBo(addCourtRequest);
		
		/******************************Calling Service********************************/
		
		CommonMessageResponse response  = courtService.addCourt(addCourtBo);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	
	/******************************Delete Court Request********************************/
	@PostMapping(path = RestMappingConstant.Court.DELETE_COURT_URI)
	public ResponseEntity<BaseApiResponse> deleteCourt(@Valid @RequestBody DeleteCourtRequest deleteCourtRequest){
		/******************************Converting Request into Bo********************************/
		DeleteCourtBo deleteCourtBo = CourtConverter.convertDeleteCourtRequestToDeleteCourtBo(deleteCourtRequest);
		
		/******************************Calling Service********************************/
		
		CommonMessageResponse response  = courtService.deleteCourt(deleteCourtBo);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	/******************************Add Court Branch Request********************************/
	@PostMapping(path = RestMappingConstant.Court.ADD_COURT_BRANCH_URI)
	public ResponseEntity<BaseApiResponse> addCourtBrnach(@Valid @RequestBody AddCourtBranchRequest addCourtBranchRequest){
		
		/******************************Converting Request into Bo********************************/
		AddCourtBranchBo addCourtBranchBo = CourtConverter.convertAddCourtBranchRequestToAddCourtBranchBo(addCourtBranchRequest);
		
		/******************************Calling Service********************************/
		
		CommonMessageResponse response  = courtService.addCourtBranch(addCourtBranchBo);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/******************************Delete Court Brnach Request********************************/
	@PostMapping(path = RestMappingConstant.Court.DELETE_COURT_BRANCH_URI)
	public ResponseEntity<BaseApiResponse> deleteCourtBranch(@Valid @RequestBody DeleteCourtBranchRequest deleteCourtRequest){
		/******************************Converting Request into Bo********************************/
		DeleteCourtBranchBo deleteCourtBo = CourtConverter.convertDeleteCourtBrnachRequestToDeleteCourtBranchBo(deleteCourtRequest);
		
		/******************************Calling Service********************************/
		
		CommonMessageResponse response  = courtService.deleteCourtBranch(deleteCourtBo);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/******************************Get Court Brnach Request********************************/
	@PostMapping(path = RestMappingConstant.Court.GET_COURT_URI)
	public ResponseEntity<BaseApiResponse> deleteCourtBranch(@Valid @RequestBody GetCourtRequest getCourtRequest,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage){
		/******************************Converting Request into Bo********************************/
		GetCourtBo getCourtBo = CourtConverter.convertGetCourtRequestToGetCourtBo(getCourtRequest);
		
		/******************************Calling Service********************************/
		
		CommonPaginationResponse response  = courtService.getCourtService(getCourtBo,pageNumber,perPage);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(response);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
}
