package com.SuperemeAppealReporter.api.ui.model.response;

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
public class CaseHistoryResponse {


	private int caseNumber;

	private int year;
	
	private int decided_day;
	
	private int decidedMonth;

	private int decidedYear;

	private String notes;
}
