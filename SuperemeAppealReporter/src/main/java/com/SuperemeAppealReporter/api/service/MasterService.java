package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.ClientIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.ui.model.response.GetCommonMasterDataResponse;

public interface MasterService {

	/**For fetching role master**/
	public GetCommonMasterDataResponse getRoleMasterData();
	
	/**for fetching country master**/
	public GetCommonMasterDataResponse getCountryMasterData();
	
	/**for fetching state master**/
	public GetCommonMasterDataResponse getStateMasterDataResponse(int countryId);
	
	/**For fetching city master**/
	public GetCommonMasterDataResponse getCityMasterDataResponse(int stateId);
	
	
	/**for generating clientId**/
	public void save(ClientIdGenerator clientIdGenerator);
	public int giveNextClientId();
	
	
	/**Returning Entities**/
	public CountryEntity getCountryEntityByCountryId(int countryId);
	public StateEntity getStateEntityByStateId(int stateId);
	public CityEntity getCityEntityByCityId(int cityId);



	
}
