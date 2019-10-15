package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequestModel {

	@NotBlank(message = "Email should not be blank")
	@Email(message = "Email is not valid")
	private String userEmail;

	@NotBlank(message = "Email should not be blank")
	@Email(message = "Email is not valid")
	private String userPassword;
}
