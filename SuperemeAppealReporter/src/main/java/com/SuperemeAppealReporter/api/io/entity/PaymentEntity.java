package com.SuperemeAppealReporter.api.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.SuperemeAppealReporter.api.enums.PaymentMode;
import com.SuperemeAppealReporter.api.enums.PaymentStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {

	
	@Column(name = "amount", nullable = false)
	private double amount;
	
	
	@Column(name = "transaction_id", nullable = false)
	private String transaction_id;
	
	@Column(name = "payment_id", nullable = true)
	private String payment_id;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private PaymentStatus paymentStatus;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "mode", nullable = false)
	private PaymentMode paymentMode;
	
	
	
		
	/**------------------------Mappings-------------------------**/
	
} 