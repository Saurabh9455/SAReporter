package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.NotEmpty;

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
public class AddPlanRequest {
	
	@NotEmpty(message = "Subscription type is required")
	private String subscriptionType;
	@NotEmpty(message = "Subscription name is required")
	private String subscriptionName;
	@NotEmpty(message = "Subscription cost is required")
	private String subscriptionCost;
	@NotEmpty(message = "Subscription description is required")
	private String subscriptionDescription;
	@NotEmpty(message = "Subscription user count is required")
	private String subscriptionParellelUserCount;

}
