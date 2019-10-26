package com.SuperemeAppealReporter.api.ui.model.response;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetCourtResponse {

	private int courtId;
	private int courtBranchId;
	private String courtName;
	private String courtBranch;
}
