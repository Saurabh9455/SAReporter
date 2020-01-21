package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

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
public class UserSignupRequest {

	@NotBlank(message = "Name should not be blank")
	@Pattern(regexp = "^[A-Za-z\\s0-9]*$",message = "Name should only contains letters or numbers")
    private String name;
	
	
	@NotBlank(message = "Enter an Email ID")
	@Email(message = "Enter a valid Email ID")
	private String email;

	
	@NotBlank(message = "Enter a password")
	//@Length(max = 15, min = 8, message = "Password should be minimum 8 characters long or maximum 15 characters long")
	private String password;

	@NotBlank(message = "Designation should not be blank")
	private String desgination;
	
	@NotNull(message = "Enter Candidate's mobile")
	@Pattern(regexp = "^[1-9]{1}[0-9]{9}$",message = "Mobile Number Should not start with 0 and should be of 10 digits.")
	private String mobile;
	

	private Integer stateId;
	

	private Integer cityId;
	

	private Integer countryId;
	
	@NotNull(message = "Zipcode should not be blank")
	@Pattern(regexp = "^[0-9]{6}$",message = "Zipcode code should be of 6 digits.")
	private String zipCode;
	
	private String address;
}
