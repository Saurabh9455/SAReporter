package com.SuperemeAppealReporter.api.io.entity;

import java.util.List;

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
@NoArgsConstructor
@Entity
@Table(name="case_entity")
public class CaseEntity extends BaseEntity {


	@Column(name = "doc_id", nullable = false)
	private long docId;
	
	
	@Column(name = "isOverruled", nullable = true)
	private boolean isOverruled = false;
	
	
	@Column(name = "isLive", nullable = true)
	private boolean isLive = true;
	
	
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
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private CourtDetailEntity courtDetailEntity;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private CitationEntity citationEntity;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntity")
	private List<AdditionalAppellantRespondentEntity> additionalAppellantRespondentEntitySet;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private CaseHistoryEntity caseHistoryEntity;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntity")
	private List<CasesRefferedEntity> casesReferredEntitySet;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "caseEntity")
	private List<HeadnoteEntity> headNoteEntitySet;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private DoubleCouncilDetailEntity doubleCouncilDetailEntity;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private SingleCouncilDetailEntity singleCouncilDetailEntity;
}
