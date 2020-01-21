package com.SuperemeAppealReporter.api.bo;

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
public class ConfirmPaymentBo {
	
	private int planId;
	private String orderId;
	private double amount;

}
