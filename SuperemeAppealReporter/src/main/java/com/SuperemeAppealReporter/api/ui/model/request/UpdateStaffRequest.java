package com.SuperemeAppealReporter.api.ui.model.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UpdateStaffRequest {

	@NotBlank(message = "Id should not be blank")
	private String staffId;

	@NotBlank(message = "Name should not be blank")
	@Pattern(regexp = "^[A-Za-z\\s0-9]*$",message = "Name should only contains letters or numbers")
	private String name;
	
	@NotBlank(message = "Designation should not be blank")
	private String desgination;
	
	@NotNull(message = "Please provide mobile no.")
	@Pattern(regexp = "^[1-9]{1}[0-9]{9}$",message = "Mobile Number Should not start with 0 and should be of 10 digits.")
	private String mobile;
	
	@Digits(integer = 2,message = "Role id should be in digits",fraction = 0)
	@NotNull(message = "Please enter role id")
	private Integer roleId;
	
	@Digits(integer = 2,message = "State id should be in digits",fraction = 0)
	@NotNull(message = "Please enter state id")
	private Integer stateId;
	
	@Digits(integer = 2,message = "City id should be in digits",fraction = 0)
	@NotNull(message = "Please enter city id")
	private Integer cityId;
	
	@Digits(integer = 2,message = "Country id should be in digits",fraction = 0)
	@NotNull(message = "Please enter country id")
	private Integer countryId;
	
	
	@NotNull(message = "Zipcode should not be blank")
	@Pattern(regexp = "^[0-9]{6}$",message = "Zipcode code should be of digits.")
	private String zipCode;
	
	@NotBlank(message = "Password should not be blank")
	private String password;


}
