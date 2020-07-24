package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.SuperemeAppealReporter.api.enums.SubscriptionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subscription_plan")
public class SubscriptionPlanEntity extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private SubscriptionType subscriptionType;
	
	
	@Column(name = "name", nullable = false)
	private String subscriptionName;
	
	
	@Column(name = "cost", nullable = false)
	private double subscriptionCost;
	
	
	@Column(name = "description", nullable = false)
	private String subscriptionDescription;
	
	@Column(name = "parellel_user_count", nullable = false)
	private String subscriptionParellelUserCount;
	
	/**------------------------Mappings-------------------------**/
	
}