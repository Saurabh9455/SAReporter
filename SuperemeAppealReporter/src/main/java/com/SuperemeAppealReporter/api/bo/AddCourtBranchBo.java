package com.SuperemeAppealReporter.api.bo;

import java.util.Set;

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
public class AddCourtBranchBo {
	private String courtId;
	private Set<String> courtBranchName;
}
