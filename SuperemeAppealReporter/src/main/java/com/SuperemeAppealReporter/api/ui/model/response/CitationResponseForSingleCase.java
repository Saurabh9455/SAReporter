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
public class CitationResponseForSingleCase {

	private String pageNumber;

	//private String year;
	private  YearResponse year;

	private String otherCitation;
	
	private CitationCategoryResponseForSingleCase citationCategoryResponse;

	private JournalResponseForSingleCase journalResponse;
	
	private SecondaryCitationResponseForSingleCase secondaryCitationResponseForSingleCase;
	
}
