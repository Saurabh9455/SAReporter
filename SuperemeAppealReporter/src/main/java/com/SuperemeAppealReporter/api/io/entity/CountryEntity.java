package com.SuperemeAppealReporter.api.io.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "country")
public class CountryEntity extends BaseEntity {

	
	@Column(name = "name", nullable = false)
	private String name;
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "countryEntity")
	private Set<StateEntity> stateEntitySet;

}
