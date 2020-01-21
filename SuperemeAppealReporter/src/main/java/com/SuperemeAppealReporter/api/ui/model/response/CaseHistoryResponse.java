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


	private String caseNumber;

	private YearResponse year;
	
	//private String decided_day;
	private YearResponse decided_day;
	
	//private String decidedMonth;

	private YearResponse decidedMonth;
	//private String decidedYear;

	private YearResponse decidedYear;
	
	private String notes;
}
