package com.SuperemeAppealReporter.api.bo;

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
public class AddPlanBo {

	private String subscriptionType;
	private String subscriptionName;
	private String subscriptionCost;
	private String subscriptionDescription;
	private String subscriptionParellelUserCount;
}
