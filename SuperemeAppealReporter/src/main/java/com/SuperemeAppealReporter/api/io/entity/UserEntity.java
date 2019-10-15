package com.SuperemeAppealReporter.api.io.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
@Table(name = "user")
public class UserEntity extends BaseEntity {

	 
	@Column(name = "client_id", nullable = true)
	private int clientId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	
	@Column(name = "email", nullable = false)
	private String email;
	
	
	@Column(name = "password", nullable = false)
	private String password;
	
	
	@Column(name = "designation", nullable = false)
	private String desgination;
	
	
	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	
	@Column(name = "type", nullable = false)
	private String userType;
	
	@Column(name = "is_email_verified", nullable = false)
	private boolean isEmailVerified = false;
	
	@Column(name = "is_subscription_active", nullable = false)
	private boolean isSubscriptionActive = false;
	
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@OneToOne(cascade = CascadeType.ALL)
	private AddressEntity addressEntity;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<RoleEntity> roleEntityList; 
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userEntity")
	private List<VerificationTokenEntity> verificationTokenEntity;
	

}