package com.SuperemeAppealReporter.api.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAcessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	
		
/**Generating Response for Access Denied APIs **/
		
    System.out.println("HEREEE WE HAVE COME");		
    response.setHeader("Content-Type", "application/json");
    Map<String,Integer> newMap = new HashMap<String,Integer>();
    newMap.put("KEY", 101);
    byte [] responseToSend = restResponseBytes(newMap);
    response.getOutputStream().write(responseToSend);
}


    private byte[] restResponseBytes(Map<String,Integer> response) throws IOException {
    String serialized = new ObjectMapper().writeValueAsString(response);
    return serialized.getBytes();
}


		
	
}

