package com.SuperemeAppealReporter.api.ui.model.response;

import java.util.List;

import com.SuperemeAppealReporter.api.shared.dto.CityDto;
import com.SuperemeAppealReporter.api.shared.dto.CountryDto;
import com.SuperemeAppealReporter.api.shared.dto.StateDto;

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
public class DahsboardResponse {

	private String name;
	private String email;
	private int clientId;
	private String password;
	private String desgination;
	private String mobile;
	private boolean isEmailVerified;
	private StateDto stateDto;
	private CountryDto countryDto;
	private CityDto cityDto;
	private boolean isSubscriptionActive;
	private String zipCode;
	private String address;
	private String profilePictureBase64EncodedString;
	
	private List<UserOrderResponse> userOrderList;
	
}
