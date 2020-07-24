package com.SuperemeAppealReporter.api.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SuperemeAppealReporter.api.bo.ForgetPasswordBo;
import com.SuperemeAppealReporter.api.bo.ResetPasswordBo;
import com.SuperemeAppealReporter.api.bo.UploadCasePdfBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.constant.SecurityConstant;
import com.SuperemeAppealReporter.api.converter.CaseConverter;
import com.SuperemeAppealReporter.api.converter.UserConverter;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.service.VerificationTokenService;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseToMyLibraryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.ForgetPasswordRequest;
import com.SuperemeAppealReporter.api.ui.model.request.LoginRequestModel;
import com.SuperemeAppealReporter.api.ui.model.request.ResetPasswordRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadPdfRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadProfilePictureRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UserSignupRequest;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.DahsboardResponse;
import com.SuperemeAppealReporter.api.ui.model.response.EmailVerificationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ForgetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.SuperemeAppealReporter.api.ui.model.response.SearchCaseListResponse;
import com.SuperemeAppealReporter.api.ui.model.response.UserSignupResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ValidateResetPasswordLinkResponse;

@RestController
@RequestMapping(path = RestMappingConstant.User.USER_BASE_URI)
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	VerificationTokenService verificationTokenService;
	
	
	/****************************************User dashboard handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.DASHBOARD_URI)
	public ResponseEntity<BaseApiResponse> dashboardHandler()
	{
		String emailId = SecurityContextHolder.getContext().getAuthentication().getName();
	    /**calling service layer**/
		DahsboardResponse dashboardResponse = userService.giveDashboardResponseService(emailId);
		

		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(dashboardResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	@PostMapping(path=RestMappingConstant.User.UPLOAD_PROFILE_PICTURE,consumes = {
	"multipart/form-data" })
	public ResponseEntity<BaseApiResponse> uploadCasePdf(@RequestParam("profilePicture")MultipartFile file)
	{
		UploadProfilePictureRequest uploadProfilePictureRequest = new UploadProfilePictureRequest();
		
		String emailId = SecurityContextHolder.getContext().getAuthentication().getName();
		uploadProfilePictureRequest.setFile(file);
		uploadProfilePictureRequest.setUserEmail(emailId);
		
		/**calling service layer**/
		CommonMessageResponse commonMessageResponse = userService.uploadProfilePicture(uploadProfilePictureRequest);
		
		
		/**returning get role master data response**/
		BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);

	}
	
	
	
	
	
	/*
	 * // @PreAuthorize("hasRole('ROLE_ADMIN')" )
	 * 
	 * @RequestMapping(path = "/secured",method = RequestMethod.GET) public String
	 * sayHello() { System.out.println("Hello"); return "Hello"; }
	 */
	/****************************************User login handler method[Just for swagger]*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.SIGN_IN_URI)
	public void login(@RequestBody LoginRequestModel loginRequestModel)
	{
	
	}
	
	/****************************************User signup handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.SIGN_UP_URI)
	public ResponseEntity<BaseApiResponse> userSingupHandler(@Valid @RequestBody UserSignupRequest userSignupRequest)
	{
		/**Converting request model to bo **/
		UserSignupBo userSignupBo = UserConverter.convertUserSignupRequestToUserSignupBo(userSignupRequest);
		userSignupBo.setRoleId(2); //2 must be the role id for USER
		
		System.out.println("-----------"+userSignupBo.getPassword());
		/**Calling service **/
		UserSignupResponse userSignupResponse = userService.userSignupService(userSignupBo);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(userSignupResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	
/****************************************User email verification handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.VERIFY_EMAIL_URI)
	public ResponseEntity<BaseApiResponse> verifyEmailHandler(@RequestParam(value = "token", required = true) String emailVerificationToken) 
	{
		
		
	  /**Calling user service **/
	   EmailVerificationResponse emailVerificationResponse = userService.verifyEmailService(emailVerificationToken);

		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(emailVerificationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	

	
/****************************************User forget password handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.FORGET_PASSWORD_URI)
	public ResponseEntity<BaseApiResponse> forgetPasswordHandler(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
		
		/**Converting request model to bo **/
		ForgetPasswordBo forgetPasswordBo = UserConverter.convertForgertPasswordRequestToForgetPasswordBo(forgetPasswordRequest);
		
		/**Calling service **/
		ForgetPasswordResponse forgetPasswordResponse = userService.forgetPasswordService(forgetPasswordBo);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(forgetPasswordResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	
	
/****************************************validate reset password link handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.VALIDATE_PASSWORD_RESET_LINK_URI)
	public ResponseEntity<BaseApiResponse> validateResetPasswordLink(@RequestParam(value="token",required = true) String passwordResetToken) {
		
		
		/**Calling service **/
		ValidateResetPasswordLinkResponse validateResetPasswordLinkResponse = verificationTokenService.validateResetPasswordLinkService(passwordResetToken);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(validateResetPasswordLinkResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
/****************************************User reset password handler method*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.RESET_PASSWORD_URI)
	public ResponseEntity<BaseApiResponse> resetPasswordHandler(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		
		/**Converting request model to bo **/
		ResetPasswordBo resetPasswordBo = UserConverter.convertResetPasswordRequestToResetPasswordBo(resetPasswordRequest);
		
		/**Calling service **/
		ResetPasswordResponse resetPasswordResponse = userService.resetPasswordService(resetPasswordBo);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(resetPasswordResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	
	
/****************************************Add case to My Library Handler*****************************************/
	
	@PostMapping(path = RestMappingConstant.User.ADD_CASE_TO_MY_LIBRARY_URI)
	public ResponseEntity<BaseApiResponse> addCaseToMyLibraryHandler(@RequestBody AddCaseToMyLibraryRequest addCaseToMyLibraryRequest) {
		
		
		/**Calling service **/
		CommonMessageResponse commonMessageResponse = userService.addCaseToMyLibraryService(addCaseToMyLibraryRequest);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	
/****************************************Add case to My Library Handler*****************************************/
	
	@GetMapping(path = RestMappingConstant.User.GET_MY_LIBRARY_CASE_LIST)
	public ResponseEntity<BaseApiResponse> getMyLibraryCaseList(@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage)  {
		
		
		//**Calling service **//*
		CommonPaginationResponse commonPaginationResponse = userService.getMyLibraryCaseList(pageNumber);
		
		//**Generating Response**//*
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	

/***************************************Log Out*****************************************************************/
	
	@GetMapping(path = RestMappingConstant.User.LOGOUT)
	public ResponseEntity<BaseApiResponse> logOut(HttpServletRequest req){
		
		
		   String header = req.getHeader(SecurityConstant.HEADER_STRING);
	        
		  String clientHeader = req.getHeader("CLIENT_TYPE"); 
	 
	    	String alltoken = header.substring(7);	
			String token_arr[] = alltoken.split("@");
			String token = token_arr[0];
			
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
			
		/**Calling service **/
		CommonMessageResponse commonMessageResponse = userService.logOut(token,email,clientHeader);
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
		
	}
	
	

/*************************************Get LoginLogout History*************************************************/
	
	@GetMapping(path = RestMappingConstant.User.LOGIN_HISTORY)
	public ResponseEntity<BaseApiResponse> getLoginHisotry(@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage)
	{
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		/**Calling service **/
		CommonPaginationResponse commonPaginationResponse = userService.getLoginHistory(email, pageNumber, perPage);
		
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	

/*************************************Get LoginLogout History*************************************************/
	
	@GetMapping(path = RestMappingConstant.User.LOGIN_HISTORY_FOR_ADMIN_PANEL)
	public ResponseEntity<BaseApiResponse> getLoginHisotryByClientForAdminUse(@RequestParam(name = AppConstant.CommonConstant.PAGE_NUMBER, defaultValue = "1") int pageNumber,
			@RequestParam(name = AppConstant.CommonConstant.PAGE_LIMIT, defaultValue = "8") int perPage, @RequestParam(name = AppConstant.CommonConstant.CLIENT_ID) String clientId)
	{
		
		
		String email  = userService.getEmailByClientId(clientId);
		
		if(email==null){
			throw new RuntimeException("Client ID provided for fetching the Client history is invalid");
		}
		
		/**Calling service **/
		CommonPaginationResponse commonPaginationResponse = userService.getLoginHistory(email, pageNumber, perPage);
		
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonPaginationResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	@GetMapping(path = RestMappingConstant.User.DELETE_MY_CASE_IN_LIBRARY)
	public ResponseEntity<BaseApiResponse> deleteMyLibraryCase(@PathVariable("docId") String docId){
		
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		/**Calling service **/
		CommonMessageResponse commonMessageResponse = userService.deleteCaseForMyLibrary(email, docId);
		
		
		/**Generating Response**/
		BaseApiResponse baseApiResponse  = ResponseBuilder.getSuccessResponse(commonMessageResponse);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse,HttpStatus.OK);
	}
	
	
		
	

	
}
