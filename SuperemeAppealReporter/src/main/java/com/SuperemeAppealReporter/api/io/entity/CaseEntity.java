package com.SuperemeAppealReporter.api.io.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="case")
public class CaseEntity extends BaseEntity {


	@Column(name = "doc_id", nullable = false)
	private long docId;
	
	
	@Column(name = "appellant", nullable = true)
	private String appellant;
	
	
	@Column(name = "respondent", nullable = true)
	private String respondent;
	
	
	@Column(name = "remarkable", nullable = true)
	private String remarkable;
	
	
	@Column(name = "judge_name", nullable = true)
	private String judgeName;
	
	
	@Column(name = "judgement_type", nullable = true)
	private String judgementType;
	
		
	@Column(name = "jdugement_order", nullable = true)
	private String judgementOrder;
	
	
	@Column(name = "case_result", nullable = true)
	private String caseResult;
	
	
	@Column(name = "original_pdf_path", nullable = true)
	private String originalPdfPath;
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@OneToOne
	private CourtDetailEntity courtDetailEntity;
	
	@OneToOne
	private CitationEntity citationEntity;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntiy")
	private Set<AdditionalAppellantRespondentEntity> additionalAppellantRespondentEntitySet;
	
	@OneToOne
	private CaseHistoryEntity caseHistoryEntity;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntiy")
	private Set<CasesRefferedEntity> casesReferredEntitySet;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntiy")
	private Set<HeadnoteEntity> headNoteEntitySet;
}