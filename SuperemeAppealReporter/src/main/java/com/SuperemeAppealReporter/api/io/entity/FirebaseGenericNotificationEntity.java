package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Entity;
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
@Table(name="firebase_generic_notification")
public class FirebaseGenericNotificationEntity extends BaseEntity
{
	
	private String notificationType;
	
	private String title;
	
	private String body;
	
	private String docId;
	
	private String status;
	
	private String remarks;
	
	
}
