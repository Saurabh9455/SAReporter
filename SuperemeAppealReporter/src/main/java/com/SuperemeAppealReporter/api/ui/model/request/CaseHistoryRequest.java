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

	private String caseNumber;
	
	private String year;
	
	private String decidedDay;
	
	private String decidedMonth;
	
	private String decidedYear;

	private String notes;
	
}
