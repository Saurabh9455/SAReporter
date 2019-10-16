package com.SuperemeAppealReporter.api.bo;

import java.util.List;

import com.SuperemeAppealReporter.api.ui.model.request.AdditionalAppellantRespondentRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CaseHistoryRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CasesRefferedRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CitationRequest;
import com.SuperemeAppealReporter.api.ui.model.request.CourtDetailRequest;
import com.SuperemeAppealReporter.api.ui.model.request.HeadnoteRequest;

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
public class AddCaseBo {

	
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
