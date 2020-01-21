package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditPlan {
    
	@Digits(integer = 100,message = "Plan id should be in digits",fraction = 0)
	@NotNull(message = "Please enter the Plan id")
	private int id;
	@NotEmpty(message = "Subscription name is required")
	private String subscriptionName;
	@NotEmpty(message = "Subscription cost is required")
	private String subscriptionCost;
	@NotEmpty(message = "Subscription description is required")
	private String subscriptionDescription;
	@NotEmpty(message = "Subscription user count is required")
	private String subscriptionParellelUserCount;
}
