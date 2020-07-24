package com.SuperemeAppealReporter.api.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.DeleteClientBo;
import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.SearchClientBo;
import com.SuperemeAppealReporter.api.bo.SearchStaffBo;
import com.SuperemeAppealReporter.api.bo.UpdateClientBo;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.converter.AdminConverter;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.service.AdminService;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.ui.model.request.AddClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.AddStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetClientListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetStaffListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.PostRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SearhClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.SearhStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdateClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdatePostRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdateStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.response.AddStaffResponse;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CustomSignupResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@RestController
@RequestMapping(path =RestMappingConstant.Admin.ADMIN_BASE_URI)
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired UserService userService;
	
   /************************************Admin get dashboard details handler handler method****************************/
	public void getDashBoardDetailsHandler()
	{
		
	}
	

	 /************************************Admin add client handler handler method****************************/
	@PostMapping(path = RestMappingConstant.Admin.ADD_CLIENT_URI)
	public ResponseEntity<BaseApiResponse> addClientHandler(@Valid @RequestBody AddClientRequest addClientRequest) {

		/** Converting request to bo **/
		UserSignupBo userSignupBo = AdminConverter.convertAddClientRequestToUserSignupBo(addClientRequest);

		/** Calling service **/
		CustomSignupResponse customUserSignupResponse = userService.customUserSignupService(userSignupBo);

		
		
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(customUserSignupResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	
		}
	
	
   /************************************Admin get client list handler handler method**********************************/
	@PostMapping(path=RestMappingConstant.Admin.GET_CLIENT_LIST_URI)
	public ResponseEntity<BaseApiResponse> getClientListHandler(@Valid @RequestBody GetClientListRequest getClientListRequest,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage) {

		/** Converting request to bo **/
		GetClientListBo getClientListBo = AdminConverter
				.convertGetClientListRequestToGetClientListResponse(getClientListRequest);

		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse = adminService.getClientListResponseService(pageNumber,
				perPage, UserType.USER.toString(), getClientListBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);

	}
	

	
   /*************************************Admin get client list handler handler method**********************************/
	public ResponseEntity<BaseApiResponse> getSingleClientHandler()
	{
		
	       return null;
	}
	
	/***********************************Add Staff*********************** */
	@PostMapping(path =RestMappingConstant.Staff.ADD_STAFF_URI)
	public ResponseEntity<BaseApiResponse> addStaffHandler(@Valid @RequestBody AddStaffRequest addStaffRequest){
		/** Converting request to bo **/
		AddStaffBo addStaffBo = AdminConverter.convertAddStaffRequestToAddStaffBo(addStaffRequest);

		/** Calling service **/
		AddStaffResponse addStaffResponse = userService.addStaff(addStaffBo);

		
		
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(addStaffResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	
	/***********************************Get All active Staff*********************** */
	
	@PostMapping(path =RestMappingConstant.Staff.GET_STAFF_LIST_URI)
	public ResponseEntity<BaseApiResponse> getStaffListHandler(@Valid @RequestBody GetStaffListRequest getStaffListRequest,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPageLimit){
		
		/** Converting request to bo **/
		GetStaffListBo getStaffListBo = AdminConverter
				.convertGetStaffListRequestToGetStaffListResponse(getStaffListRequest);
		
		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse = adminService.getStaffListResponseService(pageNumber,
				perPageLimit, getStaffListBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
		
	}
	/***********************************Delete Staff*********************** */
	@PostMapping(path =RestMappingConstant.Staff.DELETE_STAFF_URI)
	public ResponseEntity<BaseApiResponse> deleteStaff(@Valid @RequestBody DeleteStaffRequest deleteStaffRequest){
		
		/** Converting request to bo **/
		DeleteStaffBo deleteStaffBo = AdminConverter
				.convertDeleteStaffRequestToDeleteStaffBo(deleteStaffRequest);
		
		/** Calling service **/
		CommonMessageResponse deleteStaffResponse = adminService.deleteStaff(deleteStaffBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(deleteStaffResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/***********************************Update Staff*********************** */
	@PostMapping(path =RestMappingConstant.Staff.UPDATE_STAFF_URI)
	public ResponseEntity<BaseApiResponse> updateStaff(@Valid @RequestBody UpdateStaffRequest updateStaffRequest){
		
		/** Converting request to bo **/
		UpdateStaffBo updateStaffBo = AdminConverter
				.convertUpdateStaffRequestToUpdateStaffBo(updateStaffRequest);
		
		/** Calling service **/
		CommonMessageResponse successResponse =  adminService.updateStaff(updateStaffBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(successResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/***********************************Delete CLient*********************** */
	@PostMapping(path =RestMappingConstant.Admin.DELETE_CLIENT_URI)
	public ResponseEntity<BaseApiResponse> deleteClient(@Valid @RequestBody DeleteClientRequest deleteClientRequest){
		
		/** Converting request to bo **/
		DeleteClientBo deleteClientBo = AdminConverter
				.convertDeleteClientRequestToDeleteClientBo(deleteClientRequest);
		
		/** Calling service **/
		CommonMessageResponse deleteClientResponse = adminService.deleteClient(deleteClientBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(deleteClientResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/***********************************Update Staff*********************** */
	@PostMapping(path =RestMappingConstant.Admin.UPDATE_CLIENT_URI)
	public ResponseEntity<BaseApiResponse> updateClient(@Valid @RequestBody UpdateClientRequest updateClientRequest){
		
		/** Converting request to bo **/
		UpdateClientBo updateClientBo = AdminConverter
				.convertUpdateClientRequestToUpdateClientBo(updateClientRequest);
		
		/** Calling service **/
		CommonMessageResponse successResponse =  adminService.updateClient(updateClientBo);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(successResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	/***********************************Search client*************************** */
	@PostMapping(path =RestMappingConstant.Admin.SEARCH_CLIENT_URI)
	public ResponseEntity<BaseApiResponse> searchClient(@Valid @RequestBody SearhClientRequest searchClientRequest,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPageLimit){
		
		/** Converting request to bo **/
		SearchClientBo searchClientBo = AdminConverter
				.convertSearchClientRequestToSearchClientBo(searchClientRequest);
		
		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse =  adminService.searchClient(searchClientBo, pageNumber, perPageLimit);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	/***********************************Search Staff*************************** */
	@PostMapping(path =RestMappingConstant.Staff.SEARCH_STAFF_URI)
	public ResponseEntity<BaseApiResponse> searchStaff(@Valid @RequestBody SearhStaffRequest searchStaffRequest,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPageLimit){
		
		/** Converting request to bo **/
		SearchStaffBo searchStaffBo = AdminConverter
				.convertSearchStaffRequestToSearchStaffBo(searchStaffRequest);
		
		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse =  adminService.searchStaff(searchStaffBo, pageNumber, perPageLimit);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = RestMappingConstant.Staff.CHANGE_STAFF_ACTIVE_STATUS)	
	public ResponseEntity<BaseApiResponse> staffActiveInactive(@RequestParam("status") boolean status,@RequestParam("staffId")String staffId){
		
		/** Converting request to bo **/
		
		/** Calling service **/
		CommonMessageResponse commonMessageResponse =  adminService.staffActiveInactive(status,staffId);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = RestMappingConstant.Admin.CASE_OVERULED_STATUS_CHANGE_URI)	
	public ResponseEntity<BaseApiResponse> caseOveruledStatusChange(@RequestParam("status") boolean status,@RequestParam("docId")String docId){
		
		/** Converting request to bo **/
		
		/** Calling service **/
		CommonMessageResponse commonMessageResponse =  adminService.caseOveruledStatusChange(status,docId);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = RestMappingConstant.Admin.CASE_LIVE_STATUS_CHANGE_URI)	
	public ResponseEntity<BaseApiResponse> caseLiveStatusChange(@RequestParam("status") boolean status,@RequestParam("docId")String docId){
		
		/** Converting request to bo **/
		
		/** Calling service **/
		CommonMessageResponse commonMessageResponse =  adminService.caseLiveStatusChange(status,docId);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = RestMappingConstant.Admin.GET_ORDER_LIST_URI)	
	public ResponseEntity<BaseApiResponse> getOrderListHandler(@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPageLimit){
	
		
		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse = adminService.getOrderList(pageNumber,
				perPageLimit);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	
	}
	
	
	
	@PostMapping(path = RestMappingConstant.Admin.MAKE_POST_URI)	
	public ResponseEntity<BaseApiResponse> makePost( @RequestBody PostRequest postRequest){
	
		
		/** Calling service **/
		CommonMessageResponse commonMessageResponse = adminService.makePost(postRequest);
		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	
	}
	
	@PutMapping(path = RestMappingConstant.Admin.UPDATE_POST_URI)	
	public ResponseEntity<BaseApiResponse> updatePost(@RequestBody UpdatePostRequest updatePostRequest){
	
		
		/** Calling service **/
		CommonMessageResponse commonMessageResponse = adminService.updatePost(updatePostRequest);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	
	}
	
	
	@PostMapping(path = RestMappingConstant.Admin.GET_POST_URI)	
	public ResponseEntity<BaseApiResponse> getPost(@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPageLimit){
	
		
		/** Calling service **/
		CommonPaginationResponse commonPaginationResponse = adminService.getPost(pageNumber,
				perPageLimit);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse> (baseApiResponse, HttpStatus.OK);
	
	}
	
	@DeleteMapping(path = RestMappingConstant.Admin.DELETE_POST_URI)	
	public ResponseEntity<BaseApiResponse> deletePost(@PathVariable ("postId") String postId){
		

		/** Calling service **/
		CommonMessageResponse commonMessageResponse = adminService.deletePost(postId);

		/** Generating Response **/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
		
	}
	

	

	
}
