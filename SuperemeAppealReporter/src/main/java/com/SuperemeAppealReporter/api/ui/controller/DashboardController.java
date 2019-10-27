package com.SuperemeAppealReporter.api.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.service.DashBoardService;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetDashBoardResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path =RestMappingConstant.Admin.ADMIN_BASE_URI)
public class DashboardController {
	
	
	@Autowired
	DashBoardService dashBoardService;
	
	@GetMapping(value = RestMappingConstant.Admin.GET_DASHBOARD_DETAILS_URI)
	public ResponseEntity<BaseApiResponse> getDashBoardHandler()
	{
	
		/**call to get dashboard service**/
		GetDashBoardResponse getDashBoardResponse = dashBoardService.getDashBoardDetailsService();
		
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getDashBoardResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}

}
