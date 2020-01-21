package com.SuperemeAppealReporter.api.ui.model.request;

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
public class MainSearchRequest {

	private int courtId;
	private String decisionDate;
	private int caseNumber;
	private int decidedYear;
	private String appellant;
	private String respondent;
	private String actName;
	
}
