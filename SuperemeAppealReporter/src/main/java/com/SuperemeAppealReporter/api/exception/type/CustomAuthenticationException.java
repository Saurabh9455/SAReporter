package com.SuperemeAppealReporter.api.exception.type;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException{

	public CustomAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
