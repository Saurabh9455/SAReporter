package com.SuperemeAppealReporter.api.io.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authentication_and_hisotory_entity")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationAndHistoryEntity extends BaseEntity{

	@Column(name = "jwt_token",nullable = false)
	private String jwtToken;
	
	
	@Column(name = "login_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTimestamp;
	
	
	@Column(name = "logout_time", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logoutTimestamp;
	
	
	@Column(name = "active_duration", nullable = true)
	private long activeDurationInMinutes;
	
	
	
	/**Mapping**/
	 @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE}, fetch = FetchType.LAZY)
	 private UserEntity userEntity;
	
	
	
	
	
	
	
}
