package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "cases_reffered")
public class CasesRefferedEntity extends BaseEntity {

	
	
	@Column(name = "party_name", nullable = true)
	private String partyName;
	
	
	@Column(name = "cases_referred", nullable = true)
	private String casesReferred;
	
	
	/**------------------------Mappings-------------------------**/
	
	

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CaseEntity caseEntity;
}
