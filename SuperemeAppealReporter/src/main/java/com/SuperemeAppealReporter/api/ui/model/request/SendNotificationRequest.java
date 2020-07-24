package com.SuperemeAppealReporter.api.ui.model.request;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SendNotificationRequest {

	private String title;
	private String body;
	private String category; 
	private String docId;
	private String userEmail;
	
	
	
}
