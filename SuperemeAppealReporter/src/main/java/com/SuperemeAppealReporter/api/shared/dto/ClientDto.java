package com.SuperemeAppealReporter.api.shared.dto;

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
public class ClientDto {

	private int id;
	private String name;
	private String email;
	private int clientId;
	private String desgination;
	private String mobile;
/*	private String city;
	private String state;
	private String country;*/
	private StateDto state;
	private CountryDto country;
	private CityDto city;
	private String password;
	private boolean isSubscriptionActive;
	private String zipCode;
	
}
