/*package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.RestMappingConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	 
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException ex) throws IOException, ServletException {
  
    	System.out.println("HERE ");
    	
    	AppException appException = null;
    	System.out.println("---->"+httpServletRequest.getAttribute("E"));
  
    	System.out.println(ex.getClass()+"###");
    if(httpServletRequest.getRequestURI().equals("/error"))
  {
    	 appException = new AppException(ErrorConstant.InvalidCredentialError.ERROR_TYPE,
				ErrorConstant.InvalidCredentialError.ERROR_CODE,
				ErrorConstant.InvalidCredentialError.ERROR_MESSAGE);
  }
  else
  {
	 appException = new AppException(ErrorConstant.InternalServerError.ERROR_TYPE+" "+ex.getClass().getName(),
				ErrorConstant.InternalServerError.ERROR_CODE,
				ErrorConstant.InternalServerError.ERROR_MESSAGE+" "+ex.getMessage());
  }
		httpServletResponse.setHeader("Content-Type", "application/json");
		    
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		
		    byte [] responseToSend = restResponseBytes(baseApiResponse);
		   
		   
		    httpServletResponse.getOutputStream().write(responseToSend);
	}
    
    
    private byte[] restResponseBytes(BaseApiResponse baseApiResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(baseApiResponse);
    return serialized.getBytes();
}

    	
    	
		
		 * BaseApiResponse baseApiResponse = new BaseApiResponse(); AppException
		 * exception = new
		 * AppException(AppConstants.ErrorTypes.INVALID_TOKEN_ERROR,AppConstants.
		 * ErrorCodes .INVALID_TOKEN_ERROR_CODE,ErrorMessages.
		 * INVALID_TOKEN_ERROR_MESSAGE_FOR_SECURITY);
		 * baseApiResponse.setResponseData(exception);
		 * baseApiResponse.setResponseStatus(new
		 * ResponseStatus(AppConstants.StatusCodes.FAILURE));
		 * httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 * OutputStream out = httpServletResponse.getOutputStream(); ObjectMapper mapper
		 * = new ObjectMapper(); mapper.writeValue(out, baseApiResponse); out.flush();
		 
    }


*/package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	 
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException ex) throws IOException, ServletException {

      AppException   appException = new AppException(ErrorConstant.InvalidCredentialError.ERROR_TYPE,
				ErrorConstant.InvalidCredentialError.ERROR_CODE,
				ErrorConstant.InvalidCredentialError.ERROR_MESSAGE);
  

		httpServletResponse.setHeader("Content-Type", "application/json");
		    
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		
		    byte [] responseToSend = restResponseBytes(baseApiResponse);
		   
		   
		    httpServletResponse.getOutputStream().write(responseToSend);
	}
    
    
    private byte[] restResponseBytes(BaseApiResponse baseApiResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(baseApiResponse);
    return serialized.getBytes();
}

    	
    	
		/*
		 * BaseApiResponse baseApiResponse = new BaseApiResponse(); AppException
		 * exception = new
		 * AppException(AppConstants.ErrorTypes.INVALID_TOKEN_ERROR,AppConstants.
		 * ErrorCodes .INVALID_TOKEN_ERROR_CODE,ErrorMessages.
		 * INVALID_TOKEN_ERROR_MESSAGE_FOR_SECURITY);
		 * baseApiResponse.setResponseData(exception);
		 * baseApiResponse.setResponseStatus(new
		 * ResponseStatus(AppConstants.StatusCodes.FAILURE));
		 * httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 * OutputStream out = httpServletResponse.getOutputStream(); ObjectMapper mapper
		 * = new ObjectMapper(); mapper.writeValue(out, baseApiResponse); out.flush();
		 */
    }


