package com.SuperemeAppealReporter.api.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SuperemeAppealReporter.api.bo.AddCaseBo;
import com.SuperemeAppealReporter.api.bo.GetCaseListBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.converter.CaseConverter;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetCaseListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadPdfRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path=RestMappingConstant.Admin.ADMIN_BASE_URI)
public class CaseController {

	
	@Autowired
	CaseService caseService;
	
	
/****************************************Add Case handler method*****************************************/
	@PostMapping(path=RestMappingConstant.Admin.ADD_CASE_URI)
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
	
	/****************************************Upload Case Pdf*****************************************/
	@PostMapping(path=RestMappingConstant.Admin.UPLOAD_PDF_CASE_URI,consumes = {
	"multipart/form-data" })
	public ResponseEntity<BaseApiResponse> uploadCasePdf(@RequestParam("file")MultipartFile file, @RequestParam("docId") String docId)
	{
		UploadPdfRequest uploadPdfRequest = new UploadPdfRequest();
		uploadPdfRequest.setDocId(Integer.parseInt(docId));
		uploadPdfRequest.setFile(file);
		
		/**converting request to bo**/
		UploadCasePdfBo uploadCasePdfBo = CaseConverter.convertUploadPdfRequestToUploadCasePdfBo(uploadPdfRequest);
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.uploadCasePdf(uploadCasePdfBo,Integer.parseInt(docId));
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);

	}
	
	
	
	/****************************************Get Case List*****************************************/
	@PostMapping(path=RestMappingConstant.Admin.GET_CASE_LIST_URI)
	public ResponseEntity<BaseApiResponse> getCaseList(@RequestBody GetCaseListRequest getCaseListRequest ,@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage)
	{
	
		/**converting from request to bo**/
		GetCaseListBo getCaseListBo = CaseConverter.convertGetCaseListRequestToGetCaseListBo(getCaseListRequest);
		
		/**calling service layer**/
	   CommonPaginationResponse commonMessageResponse = caseService.getCaseList(getCaseListBo,pageNumber,perPage);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);

	}
}
