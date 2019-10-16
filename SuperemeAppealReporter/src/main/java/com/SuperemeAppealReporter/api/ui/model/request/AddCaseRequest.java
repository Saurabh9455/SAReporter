package com.SuperemeAppealReporter.api.ui.model.request;

import java.util.List;

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
public class AddCaseRequest {

	private long docId;
	
	private String appellant;

	private String respondent;

	private String remarkable;
	
	private String judgeName;
	
	private String judgementType;

	private String judgementOrder;

	private String caseResult;
	
	private CourtDetailRequest courtDetailRequest;

	private CitationRequest citationRequest;

	private List<AdditionalAppellantRespondentRequest> additionalAppellantRespondentRequestList;
	
	private CaseHistoryRequest caseHistoryRequest;

	private List<CasesRefferedRequest> casesReferredRequestList;
	
	private List<HeadnoteRequest> headNoteRequestList;

	
	
}
