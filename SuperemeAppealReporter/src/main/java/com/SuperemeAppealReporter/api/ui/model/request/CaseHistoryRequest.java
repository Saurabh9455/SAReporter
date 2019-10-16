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
public class CaseHistoryRequest {

	private int caseNumber;
	
	private int year;
	
	private int decidedDay;
	
	private int decidedMonth;
	
	private int decidedYear;

	private String notes;
	

}
