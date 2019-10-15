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
public class StaffDto {
	
	private int id;
	private String name;
	private String email;
	private int staffId;
	private String desgination;
	private String staffCategory;
	private String mobile;
	private String city;
	private String state;
	private String country;
	private boolean isSubscriptionActive;
}
