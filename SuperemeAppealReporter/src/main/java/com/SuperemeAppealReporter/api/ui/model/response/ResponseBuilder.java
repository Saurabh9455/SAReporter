package com.SuperemeAppealReporter.api.ui.model.response;

import com.SuperemeAppealReporter.api.constant.AppConstant;

public class ResponseBuilder {

	public static BaseApiResponse getSuccessResponse(Object responseData)  {
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCode.SUCCESS));
		baseApiResponse.setResponseData(responseData);
		
		return baseApiResponse;
	}
	
	public static BaseApiResponse getSuccessResponse()  {
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCode.SUCCESS));
		baseApiResponse.setResponseData(null);
		
		return baseApiResponse;
	}
	
	public static BaseApiResponse getFailureResponse(Object responseData)  {
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCode.FAILURE));
		baseApiResponse.setResponseData(responseData);
		
		return baseApiResponse;
	}
	
	public static BaseApiResponse getFailureResponse()  {
		
		BaseApiResponse baseApiResponse = new BaseApiResponse();
		baseApiResponse.setResponseStatus(new ResponseStatus(AppConstant.StatusCode.FAILURE));
		baseApiResponse.setResponseData(null);
		return baseApiResponse;
	}
	
}