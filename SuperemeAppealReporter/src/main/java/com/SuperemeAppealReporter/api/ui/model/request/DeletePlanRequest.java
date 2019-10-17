package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.NotEmpty;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class DeletePlanRequest {

	@NotEmpty(message = "Please provide plan id.")
	private String subscriptionPlanId;
}
