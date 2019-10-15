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
@ToString
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

	
	@Column(name = "role_type", nullable = false)
	private String name;
	

	/**------------------------Mappings-------------------------**/
   

	
}