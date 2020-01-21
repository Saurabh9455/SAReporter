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
public class CourtDetailResponseForSingleCase {
	
	private String allJudges;

	private CourtResponseForSingleCase courtResponse;
	
	private CourtBranchResponseForSingleCase courtBranchResponse;

	private CourtBenchResponseForSingleCase courtBenchResponse;
}
