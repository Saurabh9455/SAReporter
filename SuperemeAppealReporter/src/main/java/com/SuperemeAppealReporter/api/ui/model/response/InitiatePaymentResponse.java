package com.SuperemeAppealReporter.api.ui.model.response;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InitiatePaymentResponse {

	private String data_key;
	private String data_amount;
	private String data_currency;
	private String data_order_Id;
	private String data_name;
	private String data_description;
	private String data_prefill_name;
	private String data_prefil_email;
	private String data_prefill_contact;
	
}
