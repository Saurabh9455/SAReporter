package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.shared.dto.CommonDto;

public class MasterConverter {

	public static CommonDto convertRoleEntityToCommonDto(RoleEntity roleEntity)
	{
		CommonDto commonDto = new CommonDto();
		BeanUtils.copyProperties(roleEntity, commonDto);
		commonDto.setLabel(commonDto.getName());
		return commonDto;
		
	}
	
	public static CommonDto convertCountryEntityToCommonto(CountryEntity countryEntity)
	{
		CommonDto commonDto = new CommonDto();
		BeanUtils.copyProperties(countryEntity, commonDto);
		commonDto.setLabel(commonDto.getName());
		return commonDto;
	}
	
	public static CommonDto convertStateEntityToCommonto(StateEntity stateEntity)
	{
		CommonDto commonDto = new CommonDto();
		BeanUtils.copyProperties(stateEntity, commonDto);
		commonDto.setLabel(commonDto.getName());
		return commonDto;
	}
	
	public static CommonDto convertCityEntityToCommonto(CityEntity cityEntity)
	{
		CommonDto commonDto = new CommonDto();
		BeanUtils.copyProperties(cityEntity, commonDto);
		commonDto.setLabel(commonDto.getName());
		return commonDto;
	}
	
	
}
