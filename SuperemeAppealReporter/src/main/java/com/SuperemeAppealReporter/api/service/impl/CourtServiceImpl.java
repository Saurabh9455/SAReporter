package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.bo.AddCourtBo;
import com.SuperemeAppealReporter.api.bo.AddCourtBranchBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBranchBo;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SucessMessage;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.CourtDao;
import com.SuperemeAppealReporter.api.io.entity.CourtBranchEntity;
import com.SuperemeAppealReporter.api.io.entity.CourtEntity;
import com.SuperemeAppealReporter.api.service.CourtService;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

@Service
public class CourtServiceImpl implements CourtService{

	@Autowired CourtDao courtDao;
	
	@Transactional
	@Override
	public CommonMessageResponse addCourt(AddCourtBo addCourtBo) {
		String courtName = addCourtBo.getCourtName();
		
		List<String> courtBranchList = new ArrayList<String>();
		if(!addCourtBo.getCourtBranchName().isEmpty() || addCourtBo.getCourtBranchName()!=null)
			courtBranchList.addAll(addCourtBo.getCourtBranchName());
		
		//CourtEntity courtEntity = courtDao.findCourtByName(courtName);
		CourtEntity courtEntity = new CourtEntity();
		courtEntity.setCourtType(courtName);
		Set<CourtBranchEntity> courtBranchEntityList = new HashSet<CourtBranchEntity>();
		for(String branchList: courtBranchList){
			CourtBranchEntity branchEntity = new CourtBranchEntity();
			branchEntity.setBranchName(branchList);
			courtBranchEntityList.add(branchEntity);
		}
		courtEntity.setCourtBranchSet(courtBranchEntityList);
		courtDao.saveCourtDetails(courtEntity);
		CommonMessageResponse response  = new CommonMessageResponse();
		response.setMsg(SucessMessage.Court.ADDED_SUCCESS);
		return response;
	}

	@Transactional
	@Override
	public CommonMessageResponse deleteCourt(DeleteCourtBo deleteCourtBo) {
		
		int courtId = Integer.parseInt(deleteCourtBo.getCourtId());
		
		Optional<CourtEntity> courtEntity= courtDao.findCourtById(courtId);
		courtEntity.get().setActive(false);
		Set<CourtBranchEntity> courtBranchEntityList = new HashSet<CourtBranchEntity>();
		courtBranchEntityList.addAll(courtEntity.get().getCourtBranchSet());
		for(CourtBranchEntity branchEntity: courtBranchEntityList){
			branchEntity.setActive(false);
		}
		CommonMessageResponse response  = new CommonMessageResponse();
		response.setMsg(SucessMessage.Court.DELETE_SUCCESS);
		return response;
	}

	@Override
	public CommonMessageResponse addCourtBranch(AddCourtBranchBo addCourtBranchBo) {
		int courtId = Integer.parseInt(addCourtBranchBo.getCourtId());
		
		HashMap<String, String> existingBrnaches = new HashMap<String, String>();
		
		/*************************Validating if court id already exists or not*************************/
		CourtEntity courtEntity = courtDao.findCourtById(courtId).orElseThrow(() -> new AppException(ErrorConstant.InvalidCourtIdError.ERROR_TYPE,
				ErrorConstant.InvalidCourtIdError.ERROR_CODE,
				ErrorConstant.InvalidCourtIdError.ERROR_MESSAGE));
		
		/*************************Fetching all already mapped branches for this court*************************/
		List<CourtBranchEntity> branchEntityList= courtDao.findCourtBranchByName(addCourtBranchBo.getCourtBranchName(),courtEntity.getId());
		
		/*************************Adding existing mapping into map*************************/
		for(CourtBranchEntity existingBranchList : branchEntityList){
			existingBrnaches.put(existingBranchList.getBranchName(), existingBranchList.getBranchName());
		}
		
		List<String> courtBranchList = new ArrayList<String>();
		
		/*************************Adding all branch request into a List*************************/
		if(!addCourtBranchBo.getCourtBranchName().isEmpty() || addCourtBranchBo.getCourtBranchName()!=null)
			courtBranchList.addAll(addCourtBranchBo.getCourtBranchName());
		
		Set<CourtBranchEntity> courtBranchEntityList = new HashSet<CourtBranchEntity>();
		
		/*************************Adding Non existing branches into final list*************************/
		for(String branchList: courtBranchList){
			CourtBranchEntity branchEntity = new CourtBranchEntity();
			if(!existingBrnaches.containsKey(branchList)){
			branchEntity.setBranchName(branchList);
			branchEntity.setCourtEntity(courtEntity);
			courtBranchEntityList.add(branchEntity);
			}
		}
		
		/*************************Saving the record*************************/
		courtDao.saveCourtBranchDetails(courtBranchEntityList);
		CommonMessageResponse response  = new CommonMessageResponse();
		response.setMsg(SucessMessage.Court.BRANCH_ADDED_SUCCESS);
		return response;
	}

	
	@Transactional
	@Override
	public CommonMessageResponse deleteCourtBranch(DeleteCourtBranchBo deleteCourtBranchBo) {
		
		int branchId = Integer.parseInt(deleteCourtBranchBo.getCourtBranchId());
		
		CourtBranchEntity courtBranchEntity= courtDao.findCourtBranchById(branchId).orElseThrow(() -> new AppException(ErrorConstant.InvalidCourtBranchIdError.ERROR_TYPE,
				ErrorConstant.InvalidCourtBranchIdError.ERROR_CODE,
				ErrorConstant.InvalidCourtBranchIdError.ERROR_MESSAGE));
		
		courtBranchEntity.setActive(false);
		
		CommonMessageResponse response  = new CommonMessageResponse();
		response.setMsg(SucessMessage.Court.DELETE_BRANCH_SUCCESS);
		return response;
	}
}
