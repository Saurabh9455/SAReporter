package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cases_reffered")
public class CasesRefferedEntity extends BaseEntity {

	
	
	@Column(name = "party_name", nullable = true)
	private String partyName;
	
	
	@Column(name = "cases_referred", nullable = true)
	private String casesReferred;
	
	@Column(name = "linked_doc_Id")
	private String linkedDocId;
	
	
	/**------------------------Mappings-------------------------**/
	
	

	@ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
	private CaseEntity caseEntity;
}
