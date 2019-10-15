package com.SuperemeAppealReporter.api.io.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.converter.MasterConverter;
import com.SuperemeAppealReporter.api.io.dao.MasterDao;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.repository.CityRepository;
import com.SuperemeAppealReporter.api.io.repository.CountryRepository;
import com.SuperemeAppealReporter.api.io.repository.RoleRepository;
import com.SuperemeAppealReporter.api.io.repository.StateRepository;
import com.SuperemeAppealReporter.api.shared.dto.CommonDto;

@Component
public class MasterDaoImpl implements MasterDao {

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	CityRepository cityRepositry;
	
	
	
	
	@Override
	public List<CommonDto> getRoleDtoMasterData() {
		
		List<CommonDto> commonDtoMasterDataList = new ArrayList<CommonDto>();
		Iterable<RoleEntity> roleEntityIterable = roleRepository.findAll();
		
		if(roleEntityIterable==null)
		{
			return commonDtoMasterDataList;
		}
		
		/**Converting role entity to common dto**/
		for(RoleEntity roleEntity : roleEntityIterable)
		{
			CommonDto commonDto = MasterConverter.convertRoleEntityToCommonDto(roleEntity);
			commonDtoMasterDataList.add(commonDto);
		}
		
		
		return commonDtoMasterDataList;
		
		
	}
	
	
	@Override
	public List<CommonDto> getStateListByCountryId(int countryId) {
		
		List<CommonDto> commonDtoMasterDataList = new ArrayList<CommonDto>();
		List<StateEntity> stateListByCountryId = stateRepository.getStateListByCountryId(countryId);
		if(stateListByCountryId==null)
		{
			return new ArrayList<CommonDto>();
		}
		
		/**Converting state entity to common dto**/
		for(StateEntity stateEntity : stateListByCountryId)
		{
			CommonDto commonDto = MasterConverter.convertStateEntityToCommonto(stateEntity);
			commonDtoMasterDataList.add(commonDto);
		}
		
		return commonDtoMasterDataList;
	}

	@Override
	public List<CommonDto> getCityListbyStateId(int stateId) {
		
		List<CommonDto> commonDtoMasterDataList = new ArrayList<CommonDto>();
		List<CityEntity> getCityListbyStateId =  cityRepositry.getCityListByStateId(stateId);
		if(getCityListbyStateId==null)
		{
			return new ArrayList<CommonDto>();
		}
		
		/**Converting state entity to common dto**/
		for(CityEntity cityEntity : getCityListbyStateId)
		{
			CommonDto commonDto = MasterConverter.convertCityEntityToCommonto(cityEntity);
			commonDtoMasterDataList.add(commonDto);
		}
		return commonDtoMasterDataList;
		
	}

	@Override
	public List<CommonDto> getCountryMaster() {
		
		List<CommonDto> commonDtoMasterDataList = new ArrayList<CommonDto>();
		Iterable<CountryEntity> countryEntityIterable = countryRepository.findAll();
		
		if(countryEntityIterable==null)
		{
			return commonDtoMasterDataList;
		}
		
		/**Converting country entity to common dto**/
		for(CountryEntity countryEntity : countryEntityIterable)
		{
			CommonDto commonDto = MasterConverter.convertCountryEntityToCommonto(countryEntity);
			commonDtoMasterDataList.add(commonDto);
		}
		
		
		return commonDtoMasterDataList;
	}


	@Override
	public CountryEntity getCountryEntityByCountryId(int countryId) {
		
		return countryRepository.findById(countryId).orElse(new CountryEntity());
	}

	
	@Override
	public StateEntity getStateEntityByStateId(int stateId) {
		return stateRepository.findById(stateId).orElse(new StateEntity());
	}

	
	@Override
	public CityEntity getCityEntityByCityId(int cityId) {
		return cityRepositry.findById(cityId).orElse(new CityEntity());
	}



	

}
