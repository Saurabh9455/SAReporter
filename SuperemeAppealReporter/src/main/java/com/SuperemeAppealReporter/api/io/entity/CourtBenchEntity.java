package com.SuperemeAppealReporter.api.io.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
@Table(name = "court_bench")
public class CourtBenchEntity extends BaseEntity {

	
	
	@Column(name = "bench_name", nullable = true)
	private String benchName;
	
	

	
	/**------------------------Mappings-------------------------**/
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "courtBenchEntity")
		private Set<CourtDetailEntity> courtDetailSet;
	
}
