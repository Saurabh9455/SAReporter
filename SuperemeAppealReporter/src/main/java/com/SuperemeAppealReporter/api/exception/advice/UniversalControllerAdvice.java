package com.SuperemeAppealReporter.api.exception.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;

@ControllerAdvice
public class UniversalControllerAdvice extends ResponseEntityExceptionHandler {

	
	/**For handling MethodArgumentNotValidException**/
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> allBindingErrorsList = ex.getBindingResult().getFieldErrors();
		List<String> allBindingErrorsMessageList = new ArrayList<String>();
	
		for (FieldError error : allBindingErrorsList) {
			allBindingErrorsMessageList.add(error.getField() + " - " + error.getDefaultMessage());
		}
		
		
		AppException appException = new AppException(ErrorConstant.InputValidationError.ERROR_TYPE,
				ErrorConstant.InputValidationError.ERROR_CODE,allBindingErrorsMessageList.toString() );
		
       BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);

      
      
       return new ResponseEntity<Object>(baseApiResponse, HttpStatus.OK);
       
	}

	
    /**For handling AppException**/
	@ExceptionHandler(AppException.class)
	public ResponseEntity<BaseApiResponse> handleUserAlreadyExistsException(
			AppException appException) {

	    BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		return new ResponseEntity<BaseApiResponse>(baseApiResponse, HttpStatus.OK);
	}
	
	
	
}
