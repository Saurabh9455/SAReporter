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
@Table(name = "additonal_appellant_respondent_entity")
public class AdditionalAppellantRespondentEntity extends BaseEntity{

	
	@Column(name = "appellant", nullable = true)
	private String appellant;
	
	
	@Column(name = "respondent", nullable = true)
	private String respondent;
	
	
	@Column(name = "case_number", nullable = true)
	private String caseNumber;
	
	@Column(name = "year", nullable = true)
	private int year;
	
	@Column(name = "extra_case_and_year", nullable = true)
	private String extraCaseAndYear;
	
	
	/**------------------------Mappings-------------------------**/
	
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CaseEntity caseEntity;
	
	
	
	
	
}
