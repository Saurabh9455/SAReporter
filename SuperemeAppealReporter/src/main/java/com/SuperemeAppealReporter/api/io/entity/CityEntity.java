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
@NoArgsConstructor
@Entity
@Table(name = "city")
public class CityEntity extends BaseEntity {

	
	@Column(name = "name", nullable = false)
	private String name;
	
	
	/**------------------------Mappings-------------------------**/
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private StateEntity stateEntity;

}
