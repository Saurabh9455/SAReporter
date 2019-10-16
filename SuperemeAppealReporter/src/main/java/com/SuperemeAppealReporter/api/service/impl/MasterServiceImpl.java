package com.SuperemeAppealReporter.api.service.impl;

import java.util.List;

import org.dom4j.dom.DOMAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.MasterDao;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.ClientIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.DocIdGenerator;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.repository.ClientIdGeneratorRepository;
import com.SuperemeAppealReporter.api.io.repository.DocIdGeneratorRepository;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.shared.dto.CommonDto;
import com.SuperemeAppealReporter.api.ui.model.response.GetCommonMasterDataResponse;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	private MasterDao masterDao;
	
	@Autowired
	ClientIdGeneratorRepository clientIdGeneratorRepository;
	
	@Autowired
	DocIdGeneratorRepository docIdGeneratorRepository;
	
	@Override
	public GetCommonMasterDataResponse getRoleMasterData() {
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = null;
		
		try
		{
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getRoleDtoMasterData();
		
		/**Generating and sending get role mater data response **/
	    getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getRoleMasterData()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
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
		try
		{
		return masterDao.getCountryEntityByCountryId(countryId);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getCountryEntityByCountryId()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
	}

	@Override
	public StateEntity getStateEntityByStateId(int stateId) {
		try
		{
		return masterDao.getStateEntityByStateId(stateId);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getStateEntityByStateId()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
	}

	@Override
	public CityEntity getCityEntityByCityId(int cityId) {
		try
		{
		return masterDao.getCityEntityByCityId(cityId);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getCityEntityByCityId()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
	}



	@Override
	public GetCommonMasterDataResponse getCountryMasterData() {
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = null;
		
		try
		{
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getCountryMaster();
		
		/**Generating and sending get role mater data response **/
		getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getCountryMasterData()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		return getCommonMasterDataResponse;
	}



	@Override
	public GetCommonMasterDataResponse getStateMasterDataResponse(int countryId) {
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = null;
		
		try
		{
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getStateListByCountryId(countryId);
		
		/**Generating and sending get role mater data response **/
		getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getStateMasterDataResponse()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		return getCommonMasterDataResponse;
	}



	@Override
	public GetCommonMasterDataResponse getCityMasterDataResponse(int stateId) {
		
		GetCommonMasterDataResponse getCommonMasterDataResponse = null;
		try
		{
		/**Calling dao layer**/
		List<CommonDto> commonDtoMasterList = masterDao.getCityListbyStateId(stateId);
		
		/**Generating and sending get role mater data response **/
	    getCommonMasterDataResponse = new GetCommonMasterDataResponse();
		getCommonMasterDataResponse.setObjectList(commonDtoMasterList);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in MasterServiceImpl --> getCityMasterDataResponse()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		return getCommonMasterDataResponse;
	}



	@Override
	public void save(DocIdGenerator docIdGenerator) {
		docIdGeneratorRepository.save(docIdGenerator);
		
	}



	@Override
	public Integer getNextDocId() {
		// TODO Auto-generated method stub
	
		int lastDocId = docIdGeneratorRepository.giveLastDocId();
		int nextDocId = lastDocId + 1;
		return nextDocId;
	}



}
