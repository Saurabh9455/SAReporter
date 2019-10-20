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
public class CitationResponse {

	private int pageNumber;

	private int year;

	private String otherCitation;
	
	private CitationCategoryResponse citationCategoryResponse;

	private JournalResponse journalResponse;
}
