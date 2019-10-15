package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.AdminDao;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.pojo.StaffMail;
import com.SuperemeAppealReporter.api.service.AdminService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.service.NotificationService;
import com.SuperemeAppealReporter.api.service.RoleService;
import com.SuperemeAppealReporter.api.shared.dto.ClientDto;
import com.SuperemeAppealReporter.api.shared.dto.StaffDto;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.DeleteStaffResponse;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	@Override
	public CommonPaginationResponse getClientListResponseService(int pageNumber, int perPage, String userType, GetClientListBo getClientListBo) {

		CommonPaginationResponse commonPaginationResponse = null;
		try
		{
		String userCategory = getClientListBo.getClientCategory();
		
		/**This if block is executed if the user category is different**/
		if(! (userCategory.equals("ACTIVE") || userCategory.equals("INACTIVE") || userCategory.equals("ALL")))
        {
			throw new AppException(ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_TYPE,
					ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_CODE,
					ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_MESSAGE);
		}
		
		

		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPage);
		Page<UserEntity> userEntityPage = null;
		
		if(userCategory.equals("ALL"))
		{
		 userEntityPage = adminDao.getUserEntityPageByUserType(pageableRequest, userType);
		}
		else if(userCategory.equals("ACTIVE"))
		{
			userEntityPage = adminDao.getActiveUserEntityPageByUserType(pageableRequest, userType);
		}
		else if(userCategory.equals("INACTIVE"))
		{
			userEntityPage = adminDao.getInActiveUserEntityPageByUserType(pageableRequest, userType);
		}
		
		
		List<UserEntity> userEntityList = userEntityPage.getContent();
		
		/**converting user entity list to client entity dto**/
		List<ClientDto> clientDtoList = new ArrayList<ClientDto> ();
		for(UserEntity userEntity : userEntityList)
		{
			ClientDto clientDto = new ClientDto();
			BeanUtils.copyProperties(userEntity, clientDto);
			clientDto.setCountry(userEntity.getAddressEntity().getCountryEntity().getName());
			clientDto.setState(userEntity.getAddressEntity().getStateEntity().getName());
			clientDto.setCity(userEntity.getAddressEntity().getCityEntity().getName());
			clientDtoList.add(clientDto);
		}
		
		commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userEntityPage.getTotalPages());
		commonPaginationResponse.setOjectList(clientDtoList);
		
		
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in AdminServiceImpl --> getClientListResponseService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		return commonPaginationResponse;
	}

	@Override
	public CommonPaginationResponse getStaffListResponseService(int pageNumber, int perPageLimit,
			GetStaffListBo getStaffListBo) {
		
		CommonPaginationResponse commonPaginationResponse =  null;
		
		try
		{
		RoleEntity roleEntity = null;
				
				
		
				List<String> staffType = new ArrayList<String>();
	
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPageLimit);
		Page<UserEntity> userEntityPage = null;
		
		if(getStaffListBo.getStaffRole() == 0 )
		{
			System.out.println("when no search parameter is given");
			String superAdmin = UserType.SUPER_ADMIN.toString();
			String admin =UserType.ADMIN.toString();
			String dataEntryOperator =UserType.DATA_ENTRY_OPERATOR.toString();
			staffType.add(dataEntryOperator);
			staffType.add(superAdmin);
			staffType.add(admin);
		}
		else {
			roleEntity = roleService.findByRoleId(getStaffListBo.getStaffRole());
			System.out.println("keywords: "+roleEntity.getName());
			staffType.add(roleEntity.getName());
		}
		userEntityPage = adminDao.getUserEntityPageForAllStaff (staffType, pageableRequest);
		List<UserEntity> userEntityList = userEntityPage.getContent();
		
		/**converting user entity list to staff entity dto**/
		List<StaffDto> staffDtoList = new ArrayList<StaffDto> ();
		for(UserEntity userEntity : userEntityList)
		{
			StaffDto staffDto = new StaffDto();
			BeanUtils.copyProperties(userEntity, staffDto);
			staffDto.setStaffId(userEntity.getClientId());
			staffDto.setStaffCategory(userEntity.getUserType());
			staffDto.setCountry(userEntity.getAddressEntity().getCountryEntity().getName());
			staffDto.setState(userEntity.getAddressEntity().getStateEntity().getName());
			staffDto.setCity(userEntity.getAddressEntity().getCityEntity().getName());
			staffDtoList.add(staffDto);
		}
		
		commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userEntityPage.getTotalPages());
		commonPaginationResponse.setOjectList(staffDtoList);
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in AdminServiceImpl --> getStaffListResponseService()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		return commonPaginationResponse;
	}
	
	@Transactional
	@Override
	public DeleteStaffResponse deleteStaff(DeleteStaffBo deleteStaffBo) {
		
		DeleteStaffResponse deleteResponse = new DeleteStaffResponse();
		try
		{
		UserEntity userEntity = adminDao.findStaffById(deleteStaffBo.getStaffId()).orElseThrow(() -> new AppException(ErrorConstant.InvalidStaffIdError.ERROR_TYPE,
					ErrorConstant.InvalidStaffIdError.ERROR_CODE,
					ErrorConstant.InvalidStaffIdError.ERROR_MESSAGE));
		
		adminDao.deleteStaffById(userEntity.getId());
		deleteResponse.setMsg("Staff deleted Successfully");
		}
		
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in AdminServiceImpl --> deleteStaff()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		return deleteResponse;
	}

	@Transactional
	@Override
	public void updateStaff(UpdateStaffBo updateStaffBo) {
		int id = Integer.parseInt(updateStaffBo.getStaffId());
		
		try
		{
		UserEntity userEntity = adminDao.findStaffById(id).orElseThrow(() -> new AppException(ErrorConstant.InvalidStaffIdError.ERROR_TYPE,
				ErrorConstant.InvalidStaffIdError.ERROR_CODE,
				ErrorConstant.InvalidStaffIdError.ERROR_MESSAGE));
		
		CountryEntity countryEntity = masterService.getCountryEntityByCountryId(updateStaffBo.getCountryId());
		StateEntity stateEntity = masterService.getStateEntityByStateId(updateStaffBo.getStateId());
		CityEntity cityEntity = masterService.getCityEntityByCityId(updateStaffBo.getCityId());
		RoleEntity roleEntity = roleService.findByRoleId(updateStaffBo.getRoleId());
		
		userEntity.setName(updateStaffBo.getName());
		userEntity.setMobile(updateStaffBo.getMobile());
		userEntity.setPassword(updateStaffBo.getPassword());
		userEntity.setDesgination(updateStaffBo.getDesgination());
		userEntity.setUserType(roleEntity.getName());
		userEntity.getAddressEntity().setCountryEntity(countryEntity);
		userEntity.getAddressEntity().setStateEntity(stateEntity);
		userEntity.getAddressEntity().setCityEntity(cityEntity);
		userEntity.getAddressEntity().setZipcode(updateStaffBo.getZipCode());
		/** Assigning Role to User **/
		ArrayList<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(roleEntity);
		
		userEntity.setRoleEntityList(roleList);
		
		
		/** Creating OnBoardingMail object **/
		StaffMail onBoardingMail = new StaffMail();
		onBoardingMail.setBelongsTo(roleEntity.getName());
		onBoardingMail.setTo(userEntity.getEmail());
		onBoardingMail.setSubject(AppConstant.Mail.OnBoardingMail.CUSTOM_SUBJECT);
		Map<String, Object> onBoardingModel = new HashMap<String, Object>();
		onBoardingModel.put(AppConstant.Mail.EMAIL_KEY, userEntity.getEmail());
		onBoardingModel.put(AppConstant.Mail.PASSWORD_KEY, userEntity.getPassword());
		onBoardingModel.put(AppConstant.Mail.USERNAME_KEY, userEntity.getName());
		onBoardingModel.put(AppConstant.Mail.ROLE_ASSIGNED, roleEntity.getName());
		onBoardingMail.setModel(onBoardingModel);

		/** Sending OnBoaringMail **/
		String updateFlag = "Y";
		try {
			notificationService.sendStaffEmailNotification(onBoardingMail,updateFlag);
		} catch (MessagingException ex) {
			throw new AppException(ErrorConstant.SendingEmailError.ERROR_TYPE,
					ErrorConstant.SendingEmailError.ERROR_CODE, ErrorConstant.SendingEmailError.ERROR_MESSAGE);
		}
		
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in AdminServiceImpl --> updateStaff()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
	}

}
