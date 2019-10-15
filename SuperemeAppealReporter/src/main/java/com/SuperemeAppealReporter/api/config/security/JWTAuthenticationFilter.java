/*package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SecurityConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.ui.model.request.LoginRequestModel;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	 private AuthenticationManager authenticationManager;
	 
	 private UserService userService;
	   
	 public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }
	    
	 
	 @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	        try {
	       
	        	  if(userService==null){
	                  ServletContext servletContext = req.getServletContext();
	                  WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	                  userService = webApplicationContext.getBean(UserService.class);
	              }
	        	
	        	
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
			
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserEmail(),
					creds.getUserPassword(), new ArrayList<>())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	 
	   @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {
	
		 User user =  (User) auth.getPrincipal();
		 String authorities = user.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.joining(","));
		 Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);
		
		 * String token = Jwts.builder() .setSubject(user.getUsername())
		 * .setIssuedAt(new Date())
		 * .claim(SecurityConstant.JWT_AUTHORITIES_KEY,authorities)
		 * .setExpiration(expiryDate) .signWith(SignatureAlgorithm.HS512,
		 * SecurityConstant.SECRET) .compact();
		 
	        String token = Jwts.builder()
	                .setSubject(user.getUsername())
	                .setIssuedAt(new Date())
	                .claim(SecurityConstant.JWT_AUTHORITIES_KEY, authorities)
	                .setExpiration(expiryDate)
	                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
	                .compact();
	   
	        
	        *//**Checking if the email is verified. If it is verified then only the user will get token**//*
	        
	      if(userService.checkEmailVerification(user.getUsername()))
	      {
	        
	      res.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
	
	
	      res.setHeader("Content-Type", "application/json");
	  
		  BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse();
		  byte[] responseToSend = restResponseBytes(baseApiResponse);
		  res.getOutputStream().write(responseToSend);
		
	      }
	      else
	      {
	    	  res.setHeader("Content-Type", "application/json");
	    	  res.setHeader("Access-Control-Allow-Headers",
	  				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
	  						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
	    	  res.setHeader("Access-Control-Expose-Headers", "Authorization");
			AppException appException = new AppException(ErrorConstant.EmailNotVerifiedError.ERROR_TYPE,
					ErrorConstant.EmailNotVerifiedError.ERROR_CODE, ErrorConstant.EmailNotVerifiedError.ERROR_MESSAGE);
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
			byte[] responseToSend = restResponseBytes(baseApiResponse);
			res.getOutputStream().write(responseToSend);
	      }
	      return;
	    }
	   
	   
	
	private byte[] restResponseBytes(BaseApiResponse response) throws IOException {
		String serialized = new ObjectMapper().writeValueAsString(response);
		return serialized.getBytes();
	}
	 
}
*/
package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SecurityConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.ui.model.request.LoginRequestModel;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	
	 private AuthenticationManager authenticationManager;
	 
	 private UserService userService;
	   
 public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }
 
	    
	 
	 @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) {
	      
	       
	        	System.out.println("HERE");
	        	 if(userService==null){
	                  ServletContext servletContext = req.getServletContext();
	                  WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	                  userService = webApplicationContext.getBean(UserService.class);
	              }
	        	
	        	try
	        	{
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);
			
			
				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserEmail(),
					creds.getUserPassword(), new ArrayList<>())
	            );
			/*	
				  SecurityContextHolder.getContext().setAuthentication(auth);*/
				
	        	}
	        	catch(IOException ex)
	        	{
	        		throw new RuntimeException(ex);
	        	}
	        
	    }
	 
	  public JWTAuthenticationFilter() {
	super();
	// TODO Auto-generated constructor stub
}



		 @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,  
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {

	    	System.out.println("HEREEE");
		 User user =  (User) auth.getPrincipal();
		 String authorities = user.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.joining(","));
		 Date now = new Date();
	        Date expiryDate = new Date(now.getTime() + SecurityConstant.EXPIRATION_TIME);
	        String token = Jwts.builder()
	                .setSubject(user.getUsername())
	                .setIssuedAt(new Date())
	                .claim(SecurityConstant.JWT_AUTHORITIES_KEY, authorities)
	                .setExpiration(expiryDate)
	                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
	                .compact();
	   
	      
	        /**Checking if the email is verified. If it is verified then only the user will get token**/
	        
	      if(userService.checkEmailVerification(user.getUsername()))
	      {
	        
	      res.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
	
	
	      res.setHeader("Content-Type", "application/json");
	  
		  BaseApiResponse baseApiResponse = ResponseBuilder.getSuccessResponse();
		  byte[] responseToSend = restResponseBytes(baseApiResponse);
    	  res.setHeader("Content-Type", "application/json");
    	  res.setHeader("Access-Control-Allow-Headers",
  				"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
  						+ "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    	  res.setHeader("Access-Control-Expose-Headers", "Authorization");
		  res.getOutputStream().write(responseToSend);
		
	      }
	      else
	      {
	
			AppException appException = new AppException(ErrorConstant.EmailNotVerifiedError.ERROR_TYPE,
					ErrorConstant.EmailNotVerifiedError.ERROR_CODE, ErrorConstant.EmailNotVerifiedError.ERROR_MESSAGE);
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
			byte[] responseToSend = restResponseBytes(baseApiResponse);
			res.getOutputStream().write(responseToSend);
	      }
	    
	 
	      
	    }
	   
	   
	
	private byte[] restResponseBytes(BaseApiResponse response) throws IOException {
		String serialized = new ObjectMapper().writeValueAsString(response);
		return serialized.getBytes();
	}


	
	 
}
