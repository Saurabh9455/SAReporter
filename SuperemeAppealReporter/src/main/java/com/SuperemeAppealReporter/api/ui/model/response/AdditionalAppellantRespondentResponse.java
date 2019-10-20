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
public class AdditionalAppellantRespondentResponse {

	private String appellant;

	private String respondent;
	
	private String caseNumber;

	private int year;

	private String extraCaseAndYear;
}
