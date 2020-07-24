package com.SuperemeAppealReporter.api.io.entity;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_subscription_detail")
public class UserSubscriptionDetailEntity extends BaseEntity {

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	private Date startDate;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false)
	private Date endDate;
	
	
	@Column(name = "is_plan_active", nullable = false, columnDefinition = "BOOLEAN")
	private Boolean is_plan_active = false;
	
	
	
	/**------------------------Mappings-------------------------**/
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	private UserEntity userEntity;
	
	@ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	private SubscriptionPlanEntity subscriptionPlanEntity;
	
	@OneToOne
	private PaymentEntity paymentEntity;
	

}
