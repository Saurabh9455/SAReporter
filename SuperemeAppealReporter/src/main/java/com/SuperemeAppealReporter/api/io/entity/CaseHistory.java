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
@Table(name = "case_history")
public class CaseHistory extends BaseEntity {

	
	@Column(name = "decided_day", nullable = true)
	private String decided_day;
	
	
	@Column(name = "decided_month", nullable = true)
	private String decidedMonth;
	
	@Column(name = "decided_year", nullable = true)
	private String decidedYear;
	
}
