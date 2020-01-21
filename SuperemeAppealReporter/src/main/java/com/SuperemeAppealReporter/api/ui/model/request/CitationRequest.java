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
	private String pageNumber;
	private int citationCategoryId;
	private int journalId;
	private String year;
	
	private String secondaryPageNumber;
	private int secondaryCitationCategoryId;
	private int secondaryJournalId;
	private String secondaryYear;
	
	

}
