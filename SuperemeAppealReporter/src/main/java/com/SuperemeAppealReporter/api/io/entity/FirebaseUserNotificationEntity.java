package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@Table(name="firebase_user_notification")
public class FirebaseUserNotificationEntity extends BaseEntity
{
	@ManyToOne
	private FirebaseUserDeviceMappingEntity firebaseUserDeviceMappingEntity;
	
	private String notificationType;
	
	private boolean isRead;
	
	private String title;
	
	private String body;
	
	private String status;
	
	private String remarks;
	
}

