package com.SuperemeAppealReporter.api.ui.model.request;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

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
public class AddCourtRequest {
	
	@NotEmpty(message = "Please provide court name.")
	private String courtName;
	@NotEmpty(message = "Please provide atleast one branch name.")
	private Set<String> courtBranchName;
}
