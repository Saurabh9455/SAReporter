package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="firebase_user_device_mapping")
public class FirebaseUserDeviceMappingEntity extends BaseEntity
{
	@OneToOne
	private UserEntity userEntity;
	private String deviceId;
	private boolean isUserLoggedIn;
	
	
}
