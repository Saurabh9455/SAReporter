package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentStatusResponse {

	private String paymentStatus;
	private String paymentDescription;
	private String paymentIdForRazorPay;
	private Date planStartDate;
	private Date planEndDate;
	private boolean isPlanActive;
	private double paymentAmount;
	private Date paymentDate;
	private String paymentMode;
	private String orderId;
	
	
	
}
