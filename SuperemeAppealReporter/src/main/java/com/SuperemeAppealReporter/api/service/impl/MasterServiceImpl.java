package com.SuperemeAppealReporter.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.io.dao.MasterDao;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.ClientIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.repository.ClientIdGeneratorRepository;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.shared.dto.CommonDto;
import com.SuperemeAppealReporter.api.ui.model.response.GetCommonMasterDataResponse;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	private MasterDao masterDao;
	
	@Autowired
	ClientIdGeneratorRepository clientIdGeneratorRepository;
	
	@Override
	public GetCommonMasterDataResponse getRoleMasterData() {
		
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getRoleDtoMasterData();
		
		/**Generating and sending get role mater data response **/
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		return getCommonMasterDataResponse;
	}



	@Override
	public void save(ClientIdGenerator clientIdGenerator) {
		
		clientIdGeneratorRepository.save(clientIdGenerator);
		
	}



	@Override
	public int giveNextClientId() {
	
		int lastClientId = clientIdGeneratorRepository.giveLastClientId();
		int nextClientId = lastClientId+1;
		return nextClientId;
	}
	
	@Override
	public CountryEntity getCountryEntityByCountryId(int countryId) {
		return masterDao.getCountryEntityByCountryId(countryId);
	}

	@Override
	public StateEntity getStateEntityByStateId(int stateId) {
		return masterDao.getStateEntityByStateId(stateId);
	}

	@Override
	public CityEntity getCityEntityByCityId(int cityId) {
		return masterDao.getCityEntityByCityId(cityId);
	}



	@Override
	public GetCommonMasterDataResponse getCountryMasterData() {
		
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getCountryMaster();
		
		/**Generating and sending get role mater data response **/
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		return getCommonMasterDataResponse;
	}



	@Override
	public GetCommonMasterDataResponse getStateMasterDataResponse(int countryId) {
		
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getStateListByCountryId(countryId);
		
		/**Generating and sending get role mater data response **/
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		return getCommonMasterDataResponse;
	}



	@Override
	public GetCommonMasterDataResponse getCityMasterDataResponse(int stateId) {
		
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getCityListbyStateId(stateId);
		
		/**Generating and sending get role mater data response **/
		GetCommonMasterDataResponse getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		return getCommonMasterDataResponse;
	}



}
