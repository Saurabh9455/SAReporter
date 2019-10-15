package com.SuperemeAppealReporter.api.constant;

public interface AppConstant {
	
	public interface Config {

		String APPLICATION_PROPERTY_SOURCE_PATH = "classpath:application.properties";
		// String APPLICATION_PROPERTY_SOURCE_PATH = "file:////mnt/a2/properties/";
		String APPLICATION_PROPERTY_NAME = "sap";

	//	String SERVER_BASE_PATH = "/mnt/assessmentaggregator/";

	}
	
	public interface StatusCode
	{
	    String SUCCESS = "0";
	    String FAILURE = "1";
	}
	
	public interface Role
	{
		String ROLE_USER = "USER";
		String ROLE_ADMIN = "ADMIN";
		String ROLE_SUPERADMIN = "SUPER_ADMIN";
		String ROLE_DATAENTRYOPERATOR = "DATA_ENTRY_OPERATOR";
		
	}
	
	public interface Mail
	{
		String USERNAME_KEY = "userName";
		String EMAIL_KEY = "userEmail";
		String PASSWORD_KEY = "userPassword";
		String ROLE_ASSIGNED = "roleAssigned";
		public interface OnBoardingMail
		{
		   String SUBJECT = "Activate your SAR account";
		   String EMAIL_VERIFICATION_REDIRECT_URL = "http://www.google.com";
		   String EMAIL_VERIFICATION_REDIRECT_URL_KEY = "emailVerificationUrl";
		   String CUSTOM_SUBJECT = "Welcome Onboard !";
		}
		
		public interface ForgetPasswordMail
		{
			String SUBJECT = "Reset your SAR account password";
			String FORGET_PASSWORD_REDIRECT_URL = "http://www.google.com";
			String FORGET_PASSWORD_REDIRECT_URL_KEY = "forgetPasswordUrl";
		}
		
	}
	
	public interface CommonConstant
	{
		
		String PER_PAGE = "perPage";
		String PAGE_NUMBER = "pageNumber";
		String PAGE_LIMIT = "perPageLimit";
	}
}
