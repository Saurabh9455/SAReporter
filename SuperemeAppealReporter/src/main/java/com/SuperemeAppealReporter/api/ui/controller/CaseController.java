package com.SuperemeAppealReporter.api.ui.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.service.CaseService;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteCaseRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetCaseListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadPdfRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCaseListResponseForSingleCase;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path=RestMappingConstant.Admin.ADMIN_BASE_URI)
public class CaseController {

	
	@Autowired
	CaseService caseService;
	
	@Autowired
	UserService userService;
	
	
	
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
		uploadPdfRequest.setDocId(Long.parseLong(docId));
		uploadPdfRequest.setFile(file);
		
		/**converting request to bo**/
		UploadCasePdfBo uploadCasePdfBo = CaseConverter.convertUploadPdfRequestToUploadCasePdfBo(uploadPdfRequest);
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.uploadCasePdf(uploadCasePdfBo,Long.parseLong(docId));
		
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
		
		CommonPaginationResponse commonMessageResponse = null;

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		UserEntity user = userService.findByEmail(email);
		
		if (user.getUserType().equalsIgnoreCase(UserType.DATA_ENTRY_OPERATOR.toString())) {

			/** calling service layer in case of Data Entry Operator **/
			commonMessageResponse = caseService.getCaseListForDataEntryOperator(getCaseListBo, pageNumber, perPage,
					email);

		}

		else {
			/** calling service layer **/
			commonMessageResponse = caseService.getCaseList(getCaseListBo, pageNumber, perPage);
		}
		
		/**returning get case list response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);

	}
	
	/****************************************Get Pdf for Case*****************************************/
	@GetMapping(path=RestMappingConstant.Admin.GET_PDF_CASE_URI)
	public ResponseEntity<Resource> getCasePdf(@RequestParam(name = AppConstant.CommonConstant.DOC_ID) long docId,HttpServletRequest request)
	{
		
	  /**calling service layer**/
	  Resource resource =	caseService.getCasePdf(docId);
	  
      // Try to determine file's content type
      String contentType = null;
      try {
          contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
         System.out.println("=========IO=======");
      }

      // Fallback to the default content type if type could not be determined
      if(contentType == null) {
          contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(contentType))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
              .body(resource);
	  /**returning get case pdf response response**/
	//  return fileArray;
	}
	
	
	/****************************************Edit Case*****************************************/
	@PostMapping(path = RestMappingConstant.Admin.EDIT_CASE_URI)
	public ResponseEntity<BaseApiResponse> editCaseHandler(@RequestBody AddCaseRequest addCaseRequest)
	{
		/**converting request to bo**/
		AddCaseBo addCaseBo = CaseConverter.convertAddCaseRequestToAddCaseBo(addCaseRequest);
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.editCaseService(addCaseBo);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
	
	
	/****************************************Delete Case*****************************************/
	@PostMapping(path = RestMappingConstant.Admin.DELETE_CASE_URI)
	public ResponseEntity<BaseApiResponse> deleteCaseHandler(@RequestBody DeleteCaseRequest deleteCaseRequest)
	{
		/**getting docId**/
		int docId = Integer.parseInt(deleteCaseRequest.getDocId());
		
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.deleteCaseService( docId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
	
	
	
	/****************************************Add Case handler method*****************************************/
	@GetMapping(path=RestMappingConstant.Admin.GET_SINGLE_CASE_URI)
	public ResponseEntity<BaseApiResponse> getSingleCase(@PathVariable("docId") int docId)
	{
		/**converting request to bo**/
		
		
		/**calling service layer**/
		GetCaseListResponseForSingleCase getCaseListResponse = caseService.getSingleCase(docId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCaseListResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
	
	
	
	@GetMapping(path=RestMappingConstant.Admin.GET_SINGLE_CASE_URI_FOR_GUEST)
	public ResponseEntity<BaseApiResponse> getSingleCaseForGuest(@PathVariable("docId") int docId)
	{
		/**converting request to bo**/
		
		
		/**calling service layer**/
		GetCaseListResponseForSingleCase getCaseListResponse = caseService.getSingleCase(docId);
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(getCaseListResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
	
	
	@DeleteMapping(path=RestMappingConstant.Admin.DELETE_PDF_DOC)
	public ResponseEntity<BaseApiResponse> deletePdf(@PathVariable("docId") int docId)
	{
		/**converting request to bo**/
		
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = caseService.deletePdf(docId+"");
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
		
	}
	
	
}
