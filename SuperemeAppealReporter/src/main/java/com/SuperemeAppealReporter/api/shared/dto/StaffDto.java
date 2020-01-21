package com.SuperemeAppealReporter.api.shared.dto;

import com.SuperemeAppealReporter.api.ui.model.response.GetCourtDropDownResponse;

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
public class StaffDto {
	
	private int id;
	private String name;
	private String email;
	private int staffId;
	private String desgination;
	private GetCourtDropDownResponse roleDropDownResponse;
	private String staffCategory;
	private String mobile;
/*	private String city;
	private String state;
	private String country;*/
	private String password;
	private StateDto state;
	private CountryDto country;
	private CityDto city;
	private String zipcode;
	private boolean isSubscriptionActive;
	private boolean isStaffActive;
	private String address;
}
