package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SecurityConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.ui.model.response.BaseApiResponse;
import com.SuperemeAppealReporter.api.ui.model.response.ResponseBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

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
    	 try
         {

			
			/*
			 * ArrayList<String> allowedUrls = new ArrayList<String>();
			 * allowedUrls.add(RestMappingConstant.User.FULL_SIGN_IN_URI);
			 */
			 
    		 System.out.println("REQUEST's ------------>>"+req.getRequestURI());
        String header = req.getHeader(SecurityConstant.HEADER_STRING);
        
			if ((header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX) )) {

				
				/*
				 * if(allowedUrls.contains(req.getRequestURI())) {
				 * System.out.println("Contains"); chain.doFilter(req, res); return; }
				 */
				
				
				
				throw new AppException(ErrorConstant.JwtTokenNotPresentError.ERROR_TYPE,
						ErrorConstant.JwtTokenNotPresentError.ERROR_CODE,
						ErrorConstant.JwtTokenNotPresentError.ERROR_MESSAGE);
			}
			
			
       
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        if(authentication!=null)
            {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
             }
        
        }
    	catch (ExpiredJwtException ex) {
    		
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
		}
    }
    
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
       
		String token = request.getHeader(SecurityConstant.HEADER_STRING).substring(7);

		final JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstant.SECRET);

		final Claims claimsJws = jwtParser.parseClaimsJws(token).getBody();

		String user = claimsJws.getSubject(); // email should be saved as subject
		
		
		final Collection<SimpleGrantedAuthority> authorities = Arrays
				.stream(claimsJws.get(SecurityConstant.JWT_AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		
		return new UsernamePasswordAuthenticationToken(user, "", authorities);
    	
	
    }
    
    



    private byte[] restResponseBytes(BaseApiResponse baseApiResponse) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(baseApiResponse);
    return serialized.getBytes();
}

}
