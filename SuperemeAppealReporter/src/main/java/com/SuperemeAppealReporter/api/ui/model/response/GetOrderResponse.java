package com.SuperemeAppealReporter.api.ui.model.response;

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
public class GetOrderResponse {

	private String paymentId;
	private String orderId;
	private String planName;
	private String orderDate;
	private String orderTime;
	private String planStartDate;
	private String planEndDate;
	private Double amount;
	private String clientId;
	private String status;
	
	
}
