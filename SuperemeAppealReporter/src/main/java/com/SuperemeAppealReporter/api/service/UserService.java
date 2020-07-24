package com.SuperemeAppealReporter.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.ForgetPasswordBo;
import com.SuperemeAppealReporter.api.bo.ResetPasswordBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.io.entity.AuthenticationAndHistoryEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.ui.model.request.AddCaseToMyLibraryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UploadProfilePictureRequest;
import com.SuperemeAppealReporter.api.ui.model.response.AddStaffResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CustomSignupResponse;
import com.SuperemeAppealReporter.api.ui.model.response.DahsboardResponse;
import com.SuperemeAppealReporter.api.ui.model.response.EmailVerificationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ForgetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResetPasswordResponse;
import com.SuperemeAppealReporter.api.ui.model.response.UserSignupResponse;


public interface UserService extends UserDetailsService {

	public UserSignupResponse userSignupService(UserSignupBo userSignupBo);
	
	public boolean checkEmailVerification(String email);
	
	public EmailVerificationResponse verifyEmailService(String emailVerificationToken);
	
	public ForgetPasswordResponse forgetPasswordService(ForgetPasswordBo forgetPasswordBo);
	
	public ResetPasswordResponse resetPasswordService(ResetPasswordBo resetPasswordBo);

	public CustomSignupResponse customUserSignupService(UserSignupBo userSignupBo);

	public AddStaffResponse addStaff(AddStaffBo addStaffBo);
	
	public UserEntity findByEmail(String email);
	
	public DahsboardResponse giveDashboardResponseService(String emailId);
	
	public CommonMessageResponse uploadProfilePicture(UploadProfilePictureRequest uploadProfilePictureRequest);

	public CommonMessageResponse addCaseToMyLibraryService(AddCaseToMyLibraryRequest addCaseToMyLibraryRequest);
	
	public CommonPaginationResponse getMyLibraryCaseList(int pageNumber);

	public void logUserActivity(String token,String userEmail, boolean isLoginPerformed,int userType);
	
	public AuthenticationAndHistoryEntity getAuthenticationAndHistoryEntityByToken(String token);

	public CommonMessageResponse logOut(String token,String userEmail,String clientHeader);
	
	public CommonPaginationResponse getLoginHistory(String email,int pageNumber, int perPage);
	
	public String getEmailByClientId(String clientId);

	CommonMessageResponse deleteCaseForMyLibrary(String userEmail, String docId);
	
}
