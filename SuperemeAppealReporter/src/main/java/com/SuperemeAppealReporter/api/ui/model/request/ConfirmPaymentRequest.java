package com.SuperemeAppealReporter.api.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfirmPaymentRequest {
	
	private int planId;
	private String orderId;
	private double amount;

}

