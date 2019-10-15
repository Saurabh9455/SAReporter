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
	
	
}
