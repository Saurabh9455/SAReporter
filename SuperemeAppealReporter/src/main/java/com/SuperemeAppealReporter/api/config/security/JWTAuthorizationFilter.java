package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.SuperemeAppealReporter.api.constant.SecurityConstant;
import com.SuperemeAppealReporter.api.io.entity.AuthenticationAndHistoryEntity;
import com.SuperemeAppealReporter.api.service.UserService;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	
	 
	 private UserService userService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager, authenticationEntryPoint);
	}
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	
	


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
    	 

			System.out.println("HEREsss");
			/*
			 * ArrayList<String> allowedUrls = new ArrayList<String>();
			 * allowedUrls.add(RestMappingConstant.User.FULL_SIGN_IN_URI);
			 */
			 
    	//	 System.out.println("REQUEST's ------------>>"+req.getRequestURI());
        String header = req.getHeader(SecurityConstant.HEADER_STRING);
        
        /**added for clinet history**/
    	String alltoken = header.substring(7);	
		String token_arr[] = alltoken.split("@");
		String token = token_arr[0];
		
		 if(userService==null){
             ServletContext servletContext = req.getServletContext();
             WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
             userService = webApplicationContext.getBean(UserService.class);
         }
		 
		 boolean loginCanOccur = false;
		 if("WEB_APP".equals(req.getHeader("CLIENT_TYPE"))){
		 AuthenticationAndHistoryEntity authenticationAndHistoryEntity = userService.getAuthenticationAndHistoryEntityByToken(token);
		 
		 
		 if(authenticationAndHistoryEntity!=null ){
			
			 loginCanOccur = authenticationAndHistoryEntity.getActive();
		 }
		 }
		
		/**Client history end**/
        
		 else if("MOBILE_APP".equals(req.getHeader("CLIENT_TYPE"))){
			 loginCanOccur=true;       
		  }
			if ((header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX) ) || !loginCanOccur) {

				
			 chain.doFilter(req, res); 
	         return;
			}
			
			
       
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        chain.doFilter(req, res);
    
    }
/*    	catch (ExpiredJwtException ex) {
    		
			AppException appException = new AppException(ErrorConstant.ExpiredJwtTokenError.ERROR_TYPE,
					ErrorConstant.ExpiredJwtTokenError.ERROR_CODE, ErrorConstant.ExpiredJwtTokenError.ERROR_MESSAGE);
		
			res.setHeader("Content-Type", "application/json");
		    
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		
		    byte [] responseToSend = restResponseBytes(baseApiResponse);
		   
		    res.getOutputStream().write(responseToSend);

    	
    	}
    	catch(UnsupportedJwtException ex)
    	{
    		AppException appException = new AppException(ErrorConstant.UnsupportedJwtTokenError.ERROR_TYPE,
					ErrorConstant.UnsupportedJwtTokenError.ERROR_CODE, ErrorConstant.UnsupportedJwtTokenError.ERROR_MESSAGE);
    	
    		
			res.setHeader("Content-Type", "application/json");
		    
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		
		    byte [] responseToSend = restResponseBytes(baseApiResponse);
		   
		    res.getOutputStream().write(responseToSend);
    	
    	}
    	catch(MalformedJwtException ex)
    	{
    		AppException appException = new AppException(ErrorConstant.MalformedJwtTokenError.ERROR_TYPE,
					ErrorConstant.MalformedJwtTokenError.ERROR_CODE, ErrorConstant.MalformedJwtTokenError.ERROR_MESSAGE);
    	
    		
			res.setHeader("Content-Type", "application/json");
		    
			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
		
		    byte [] responseToSend = restResponseBytes(baseApiResponse);
		   
		    res.getOutputStream().write(responseToSend);
    		
    	}
    	 
    	 catch(AppException appException)
    	 {

 			res.setHeader("Content-Type", "application/json");
 		    
 			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
 		
 		    byte [] responseToSend = restResponseBytes(baseApiResponse);
 		   
 		    res.getOutputStream().write(responseToSend);
    	 }
    	 
		catch (Exception ex) {
			
			
			AppException appException = new AppException(ErrorConstant.JwtTokenParsingError.ERROR_TYPE,
					ErrorConstant.JwtTokenParsingError.ERROR_CODE,
					ErrorConstant.JwtTokenParsingError.ERROR_MESSAGE + ":- " + ex.getClass());
			

 			res.setHeader("Content-Type", "application/json");
 		    
 			BaseApiResponse baseApiResponse = ResponseBuilder.getFailureResponse(appException);
 		
 		    byte [] responseToSend = restResponseBytes(baseApiResponse);
 		   
 		    res.getOutputStream().write(responseToSend);
		}*/
    
    
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
       
    	
    	String token1 = request.getHeader(SecurityConstant.HEADER_STRING);

		if(token1!=null)
		{	
		
		String alltoken = request.getHeader(SecurityConstant.HEADER_STRING).substring(7);	
		String token_arr[] = alltoken.split("@");
		String token = token_arr[0];
		System.out.println("-----------TOKEN----------"+token);
		final JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstant.SECRET);

		final Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();

		String user = claimsJws.getSubject(); // email should be saved as subject
		
		
		final Collection<SimpleGrantedAuthority> authorities = Arrays
				.stream(claimsJws.get(SecurityConstant.JWT_AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		if(user!=null)
		{
		return new UsernamePasswordAuthenticationToken(user, "", authorities);
		}
		return null;
    	}
		return null;
    
    	
    }
    
    
    



    private byte[] restResponseBytes(BaseApiResponse baseApiResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(baseApiResponse);
    return serialized.getBytes();
}

}
