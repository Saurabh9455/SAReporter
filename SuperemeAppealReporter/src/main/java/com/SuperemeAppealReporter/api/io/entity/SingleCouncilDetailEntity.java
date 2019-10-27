package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "single_council_detail_entity")
public class SingleCouncilDetailEntity extends BaseEntity {
	
	
	@Column(name = "petitioner_name", nullable = true)
	private String petionerName;
	

	/**------------------------Mappings-------------------------**/

	@OneToOne(cascade = CascadeType.REFRESH)
	private CaseEntity caseEntity;
	
}
