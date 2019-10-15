package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.NotBlank;

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
public class GetClientListRequest {

	@NotBlank(message = "Category should not be blank")
	private String clientCategory;
	
}
