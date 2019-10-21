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
public class DoubleCouncilDetailRequest {

	private String advocateForRespondent;
	
	private String advocateForAppellant;
	
	private String extraCouncilDetails;
	
	
}
