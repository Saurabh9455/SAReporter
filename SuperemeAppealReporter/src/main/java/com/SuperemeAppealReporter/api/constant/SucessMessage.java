package com.SuperemeAppealReporter.api.constant;

public interface SucessMessage {

	public interface UserSignup
	{
		String USER_SIGNUP_SUCCESS = "User registered Successfully";
	}
	
	public interface EmailVerify
	{
		String EMAIL_VERIFY_SUCCESS = "Email has been verified. Please login.";
	}
	
	public interface ForgetPassword
	{
		String FORGET_PASSWORD_SUCCESS = "Forget password initated successfully";
	}
	
	public interface ValidatePasswordResetLink
	{
		String VALIDATE_PASSWORD_RESET_LINK_SUCCESS = "The password reset link is OK";
	}
	
	public interface ResetPasswordSuccess
	{
		String RESET_PASSWORD_SUCCESS = "Password changed successfully";
	}
	
	public interface CustomUserSignup
	{
		String CUSTOM_USER_SIGNUP_SUCCESS = "User registered Successfully";
	}
	public interface ClientMessage
	{
		String CLIENT_UPDATED = "Client updated successfully.";
		String CLIENT_DELETED = "Client deleted successfully.";
	}
	public interface StaffMessage
	{
		String STAFF_UPDATED = "Staff updated successfully.";
		String STAFF_DELETED = "Staff deleted successfully.";
	}
	

	public  interface CaseMessage
	{
		String CASE_CREATED_SUCESS = "Case successfully created.";
	}
	public interface SubscriptionMessage
	{
		String PLAN_ADDED = "Subscription plan added successfully.";
		String PLAN_DELETED = "Subscription plan deleted successfully.";
		

	}
	
	public interface FileUploadSuccess
	{
		String FILE_UPLOAD_SUCCESS = "PDF File Uploaded Successfully";
	}
	
	public interface Court
	{
		String ADDED_SUCCESS = "Court added successfully";
		String DELETE_SUCCESS = "Court deleted successfully";
		String BRANCH_ADDED_SUCCESS = "Court branch added successfully";
		String DELETE_BRANCH_SUCCESS = "Court branch deleted successfully";
	}
}
