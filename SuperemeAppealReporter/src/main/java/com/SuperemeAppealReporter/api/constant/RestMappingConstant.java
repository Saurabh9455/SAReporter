package com.SuperemeAppealReporter.api.constant;

public interface RestMappingConstant {

	String APP_BASE_URI = "/SuperemeAppealReporter/v1/api";
	
	public interface User
	{
		String USER_BASE_URI = APP_BASE_URI+"/user";
		
		// sign in urls for security filter
		String USER_SIGN_IN_DEV_URL = "/SuperemeAppealReporter/SuperemeAppealReporter/v1/api/user/signin";
		String USER_SIGN_IN_LOCAL_URL ="/SuperemeAppealReporter/v1/api/user/signin"; 
		
		String SIGN_UP_URI = "/signup";
		String FULL_SIGN_UP_URI = "/SuperemeAppealReporter/v1/api/user/signup";
		
		String VERIFY_EMAIL_URI = "/verifyEmail";
		String FULL_VERIFY_EMAIL_URI  = "/SuperemeAppealReporter/v1/api/user/verifyEmail";
		
		
		String SIGN_IN_URI = "/signin";
		String FULL_SIGN_IN_URI = "/SuperemeAppealReporter/v1/api/user/signin";
		
		String FORGET_PASSWORD_URI = "/forgetPassword";
		String FULL_FORGET_PASSWORD_URI = "/SuperemeAppealReporter/v1/api/user/forgetPassword";
		
        String VALIDATE_PASSWORD_RESET_LINK_URI = "/validateResetPasswordLink";
        String FULL_VALIDATE_PASSWORD_RESET_LINK_URI = "/SuperemeAppealReporter/v1/api/user/validateResetPasswordLink";
		
		
		String RESET_PASSWORD_URI = "/resetPassword";
		String FULL_RESET_PASSWORD_URI = "/SuperemeAppealReporter/v1/api/user/resetPassword";
		

		
	}
	
	public interface Admin
	{
		String ADMIN_BASE_URI = APP_BASE_URI+"/admin";	
		
		String GET_CLIENT_LIST_URI = "/getClientList";
		String FULL_GET_CLIENT_LIST_URI = "/SuperemeAppealReporter/v1/api/admin/getClientList";
		
		String ADD_CLIENT_URI = "/addClient";
		String FULL_ADD_CLIENT_URI = "/SuperemeAppealReporter/v1/api/admin/addClient";
		
		String DELETE_CLIENT_URI = "/deleteClient";
		String UPDATE_CLIENT_URI = "/updateClient";

		String SEARCH_CLIENT_URI = "/searchClient";
		
		String ADD_CASE_URI = "/addCase";
		String FULL_ADD_CASE_URI = "/SuperemeAppealReporter/v1/api/admin/addCase";
		
		String UPLOAD_PDF_CASE_URI = "/uploadCasePf";
		String FULL_UPLOAD_PDF_CASE_URI = "/SuperemeAppealReporter/v1/api/admin/uploadCasePf";
		
		String GET_CASE_LIST_URI =  "/getCaseList";
		String FULL_GET_CASE_LIST_URI  = "/SuperemeAppealReporter/v1/api/admin/getCaseList";
		
		
	}
	
	public interface Master
	{
		String MASTER_BASE_URI = APP_BASE_URI+"/master";
		
		String GET_ROLE_MASTER_DATA_URI = "/getRoleMasterData";
		String FULL_GET_ROLE_MASTER_DATA_URI = "/SuperemeAppealReporter/v1/api/master/getRoleMasterData";
		
		String GET_COUNTRY_MASTER_DATA_URI = "/getCountryMasterData";
		String FULL_GET_COUNTRY_MASTER_DATA_URI = "/SuperemeAppealReporter/v1/api/master/getCountryMasterData";
		
		
		String GET_STATE_MASTER_DATA_URI = "/getStateMasterData/{countryId}";
		String FULL_GET_STATE_MASTER_DATA_URI = "/SuperemeAppealReporter/v1/api/master/getStateMasterData/{countryId}";
	
		String GET_CITY_MASTER_DATA_URI = "/getCityMasterData/{stateId}";
		String FULL_CITY_MASTER_DATA_URI = "/SuperemeAppealReporter/v1/api/master/getCityMasterData/{stateId}";
	
	    String GET_NEXT_DOC_ID_URI = "/getNextDocId";
	    String FULL_GET_NEXT_DOC_ID_URI = "/SuperemeAppealReporter/v1/api/master/getNextDocId";
	
	}
	
	public interface Staff
	{
		String GET_STAFF_LIST_URI = "/getStaffList";
		String ADD_STAFF_URI = "/addStaff";
		String DELETE_STAFF_URI = "/deleteStaff";
		String UPDATE_STAFF_URI = "/updateStaff";
		String SEARCH_STAFF_URI = "/searchStaff";
	}
	
	public interface SubscriptionPlan
	{
		String ADD_PLAN_URI = "/addPlan";
		String DELETE_PLAN_URI = "/deletePlan";
		String GET_PLAN_URI = "/getPlanList";	
	}
	
	public interface Court
	{
		String ADD_COURT_URI = "/addCourt";
		String DELETE_COURT_URI = "/deleteCourt";
		String ADD_COURT_BRANCH_URI = "/addCourtBranch";
		String DELETE_COURT_BRANCH_URI = "/deleteCourtBranch";
	}
}
