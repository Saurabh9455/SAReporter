package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserOrderResponse {

	private Date planStartDate;
	private Date planEndDate;
	private boolean isPlanActive;
	private double paymentAmount;
	private Date paymentDate;
	private String paymentMode;
	private String transactionId;
	private String paymentStatus;
	private String paymentId;
	private String planType;
	private boolean isFuturePlan;
	
	
}
