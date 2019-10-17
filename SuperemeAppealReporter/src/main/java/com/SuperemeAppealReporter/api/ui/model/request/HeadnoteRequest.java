package com.SuperemeAppealReporter.api.ui.model.request;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeadnoteRequest {

	private String actname1;

	private String actname2;
	
	private String actname3;
	
	private String section1;
	
	private String section2;

	private String section3;

	private String topic;

	private String priority;
	
	private String headnote;
	
	
}
