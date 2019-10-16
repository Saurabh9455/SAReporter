package com.SuperemeAppealReporter.api.ui.model.request;

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
public class CitationRequest {

	private String otherCitation;
	private int pageNumber;
	private int citationCategoryId;
	private int journalId;
	private int year;
	
	

}
