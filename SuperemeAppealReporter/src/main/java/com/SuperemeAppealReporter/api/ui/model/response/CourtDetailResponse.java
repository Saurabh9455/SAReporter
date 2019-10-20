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
public class CourtDetailResponse {
	
	private String allJudges;

	private CourtResponse courtResponse;
	
	private CourtBranchResponse courtBranchResponse;

	private CourtBenchResponse courtBenchResponse;
}
