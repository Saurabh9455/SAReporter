package com.SuperemeAppealReporter.api.bo;

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
public class GetCaseListBo {

	private String caseCategory;
	private String courCategory;
	private String overRuled;
	private String live;
	private String searchValue;
}
