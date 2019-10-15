package com.SuperemeAppealReporter.api.exception.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(value = {"cause","stackTrace","suppressed","localizedMessage"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	
	private String errorType;
	
	private String errorCode;
	
	private String message;
	
	
}

