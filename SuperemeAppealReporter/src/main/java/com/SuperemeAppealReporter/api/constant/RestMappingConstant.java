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
		
		String DASHBOARD_URI = "/getDashBoard";
		String FULL_DASHBOARD_URI = "/SuperemeAppealReporter/v1/api/user/getDashBoard";
		
		String FORGET_PASSWORD_URI = "/forgetPassword";
		String FULL_FORGET_PASSWORD_URI = "/SuperemeAppealReporter/v1/api/user/forgetPassword";
		
        String VALIDATE_PASSWORD_RESET_LINK_URI = "/validateResetPasswordLink";
        String FULL_VALIDATE_PASSWORD_RESET_LINK_URI = "/SuperemeAppealReporter/v1/api/user/validateResetPasswordLink";
		
		
		String RESET_PASSWORD_URI = "/resetPassword";
		String FULL_RESET_PASSWORD_URI = "/SuperemeAppealReporter/v1/api/user/resetPassword";
		

		String MAIN_SEARCH_URI = "/mainSearch";
		String FULL_MAIN_SEARCH_URI = "/SuperemeAppealReporter/v1/api/user/mainSearch";
		
		String UPLOAD_PROFILE_PICTURE = "/uploadProfilePicture";
		
		String ADD_CASE_TO_MY_LIBRARY_URI = "/addCaseToMyLibrary";
		
		String GET_MY_LIBRARY_CASE_LIST = "/getMyLibraryCaseList";
		
		String LOGOUT = "/logout";
		
		String LOGIN_HISTORY = "/loginHistory";
		
		String LOGIN_HISTORY_FOR_ADMIN_PANEL = "/loginHistoryByClientIdForAdminPanel";
		
		String DELETE_MY_CASE_IN_LIBRARY = "/deleteCaseFromMyLibrary/{docId}";
		
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
		
		String GET_PDF_CASE_URI ="/getCasePdf";
		String FULL_GET_CASE_PDF_URI = "/SuperemeAppealReporter/v1/api/admin/getCasePdf";
		
		String GET_DASHBOARD_DETAILS_URI  = "/getDashBoard";
		String FULL_GET_DASHBOARD_DETAILS_URI = "/SuperemeAppealReporter/v1/api/admin/getDashBoard";
		
		String EDIT_CASE_URI = "/editCase";
		String FULL_EDIT_CASE_URI = "/SuperemeAppealReporter/v1/api/admin/editCase";
		
		String DELETE_CASE_URI = "/deleteCase";
		String FULL_DELETE_CASE_URI = "/SuperemeAppealReporter/v1/api/admin/deleteCase";
		
		String GET_SINGLE_CASE_URI = "/getSingleCase/{docId}";
		String GET_SINGLE_CASE_FULL_URI = "/SuperemeAppealReporter/v1/api/admin/getSingleCase/{docId}";
	
		
		String CASE_OVERULED_STATUS_CHANGE_URI = "/changeCaseOveruledStatus";
		String CASE_LIVE_STATUS_CHANGE_URI = "/changeCaseLiveStatus";
		
		String GET_ORDER_LIST_URI = "/getOrderList";
		
		String MAKE_POST_URI = "/makePost";
		
		String UPDATE_POST_URI = "/updatePost";
		
		String GET_POST_URI = "/getPostList";
		
		String DELETE_POST_URI = "/deletePost/{postId}";
		
		String GET_SINGLE_CASE_URI_FOR_GUEST = "/getSingleCaseForGuest/{docId}";
		
		String DELETE_PDF_DOC = "/deletePdf/{docId}";
		
		
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
	    
	    String ADD_CASE_MASTER_API = "/addCaseMasterAPI";
	    
	    String OTHER_CITATION_JOURNAL_MASTER_URI = "/getOtherCitationJournals";
	
	}
	
	
	
	public interface Staff
	{
		String GET_STAFF_LIST_URI = "/getStaffList";
		String ADD_STAFF_URI = "/addStaff";
		String DELETE_STAFF_URI = "/deleteStaff";
		String UPDATE_STAFF_URI = "/updateStaff";
		String SEARCH_STAFF_URI = "/searchStaff";
		String CHANGE_STAFF_ACTIVE_STATUS ="/staffActiveInActive";
	}
	
	public interface SubscriptionPlan
	{
		String ADD_PLAN_URI = "/addPlan";
		String DELETE_PLAN_URI = "/deletePlan";
		String GET_PLAN_URI = "/getPlanList";
		String EDIT_PLAN_URI = "/editPlan";
	}
	
	public interface Court
	{
		String ADD_COURT_URI = "/addCourt";
		String DELETE_COURT_URI = "/deleteCourt";
		String ADD_COURT_BRANCH_URI = "/addCourtBranch";
		String DELETE_COURT_BRANCH_URI = "/deleteCourtBranch";
		String GET_COURT_URI = "/getCourtList";
		String GET_COURT_URI_V2 = "/getCourtListV2";
		String GET_COURT_FOR_DROPDOWN = "/getCourtForDropDown";	
		String GET_COURT_BRANCH_BY_COURT_ID = "/getCourtBranchByCourtId/{courtId}";
	}
	
	public interface Payment
	{
		String PAYMENT_BASE_URI = "/SuperemeAppealReporter/v1/api/user/payment";
		String INITIATE_PAYMENT_URI = "/initiatePayment";
		String INITIATE_PAYMENT_FULL_URI = "/SuperemeAppealReporter/v1/api/user/payment/initiatePayment";
	    String CONFIRM_PAYMENT_URI = "/confirmPayment";
	    String CONFIRM_PAYMENT_FULL_URI = "/SuperemeAppealReporter/v1/api/user/payment/confirmPayment";
	}
	
	public interface Search
	{
		String DASHBOARD_SEARCH_URI = "/dashboardSearch";
		String DASHBOARD_SEARCH_FULL_URI = "/SuperemeAppealReporter/v1/api/user/dashboardSearch";
		String ACT_NAME_MASTER_SEARCH = "/actNameMasterSearch";
		String TOPIC_MASTER_SEARCH = "/topicMasterSearch";
	}
	
	public interface Notification{
		String NOTIFICATION_BASE_URI = APP_BASE_URI+"/notification";
		String SEND_NOTIFICATION_URI = "/send";
		String REGISTER_DEVICE_URI = "/registerDevice";
		String GET_NOTIFICATION_LIST = "/getNotificationList";
		
	}
}
