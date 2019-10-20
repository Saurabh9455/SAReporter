package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.Date;
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
public class GetCaseListResponse {

	private int docId;

	private boolean isOverruled ;

	private boolean isLive ;
	
	private String updatedBy;
	
	private String createdBy;
	
	private Date createdDate;
	
	private Date updatedDate;

	private String appellant;
	
	private String respondent;

	private String remarkable;

	private String judgeName;

	private String judgementType;

	private String judgementOrder;
	
	private String caseResult;

	private String originalPdfPath;
	
	private CourtDetailResponse courtDetailResponse;
	
	private CitationResponse citationResponse;
	
	private List<AdditionalAppellantRespondentResponse> additionalAppellantRespondentResponseList;
	
	private CaseHistoryResponse caseHistoryResponse;

	private List<CasesReferredResponse> casesReferredResponseList;
	
	private List<HeadnoteResponse> headnoteResponseList;
	
}
