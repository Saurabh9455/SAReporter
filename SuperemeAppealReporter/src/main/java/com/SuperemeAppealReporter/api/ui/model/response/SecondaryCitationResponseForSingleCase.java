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
public class SecondaryCitationResponseForSingleCase {


		private String pageNumber;

		private  YearResponse year;
		
		private CitationCategoryResponseForSingleCase citationCategoryResponse;

		private JournalResponseForSingleCase journalResponse;
	
}
