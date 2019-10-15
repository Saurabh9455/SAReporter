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
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "court_detail")
public class CourtDetailEntity extends BaseEntity{

	
	@Column(name = "all_judges", nullable = true)
	private String allJudges;
	
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CourtEntity courtEntity;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CourtBranchEntity courtBranchEntity;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private CourtBenchEntity courtBenchEntity;
	
	
}
