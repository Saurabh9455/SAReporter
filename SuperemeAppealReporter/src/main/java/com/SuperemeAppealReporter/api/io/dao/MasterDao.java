package com.SuperemeAppealReporter.api.io.dao;

import java.util.List;

import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.shared.dto.CommonDto;

public interface MasterDao {

	
	public List<CommonDto> getRoleDtoMasterData();
	

	
	
	
	/**Returning entities**/
	public CountryEntity getCountryEntityByCountryId(int countryId) ;

	public StateEntity getStateEntityByStateId(int stateId) ;

	public CityEntity getCityEntityByCityId(int cityId);
	
	
	public List<CommonDto> getStateListByCountryId(int countryId);

	public List<CommonDto> getCityListbyStateId(int stateId);
	
	
	public List<CommonDto> getCountryMaster();
	
}
