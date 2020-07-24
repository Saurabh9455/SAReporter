package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "double_council_detail_entity")
public class DoubleCouncilDetailEntity extends BaseEntity {
	
	
	@Column(name = "advocate_for_appellant", nullable = true)
	private String advocateForAppellant;
	
	@Column(name = "advocate_for_respondent", nullable = true)
	private String advocateForRespondent;
	
	@Column(name = "extra_council_details", nullable = true)
	private String extraCouncilDetails;
	
	
	

	/**------------------------Mappings-------------------------**/

/*	@OneToOne(cascade = CascadeType.REFRESH,fetch=FetchType.LAZY)
	private CaseEntity caseEntity;*/
	
}