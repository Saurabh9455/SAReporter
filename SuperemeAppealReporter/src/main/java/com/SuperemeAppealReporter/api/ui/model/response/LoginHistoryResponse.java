package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginHistoryResponse {


	private String jwtToken;
	
	private Date loginTimestamp;
	
	private Date logoutTimestamp;

	private long activeDurationInMinutes;
	
}
