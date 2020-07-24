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
@NoArgsConstructor
@Entity
@Table(name = "court")
public class CourtEntity extends BaseEntity {

	@Column(name = "court_type", nullable = true)
	private String courtType;

	
	

	/**------------------------Mappings-------------------------**/
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "courtEntity")
	private Set<CourtBranchEntity> courtBranchSet;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "courtEntity")
	private Set<CourtDetailEntity> courtDetailSet;
   
}
