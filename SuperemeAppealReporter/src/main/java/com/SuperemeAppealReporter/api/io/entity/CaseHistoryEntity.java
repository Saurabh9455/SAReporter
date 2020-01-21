package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "case_history")
public class CaseHistoryEntity extends BaseEntity{
	
	
	@Column(name = "case_number", nullable = true)
	private String caseNumber;
	
	@Column(name = "year", nullable = true)
	private String year;
	
	
	@Column(name = "decided_day", nullable = true)
	private String decided_day;
	
	
	@Column(name = "decided_month", nullable = true)
	private String decidedMonth;
	
	@Column(name = "decided_year", nullable = true)
	private String decidedYear;
	
	
	@Column(name = "notes", nullable = true)
	private String notes;
	
	
	/**------------------------Mappings-------------------------**/
	
/*	@OneToOne
	private CaseEntity caseEntity;*/
	

}
