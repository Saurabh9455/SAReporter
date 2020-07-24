package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.SuperemeAppealReporter.api.bo.DeleteClientBo;
import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.SearchClientBo;
import com.SuperemeAppealReporter.api.bo.SearchStaffBo;
import com.SuperemeAppealReporter.api.bo.UpdateClientBo;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
import com.SuperemeAppealReporter.api.constant.AppConstant;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.constant.SucessMessage.ClientMessage;
import com.SuperemeAppealReporter.api.constant.SucessMessage.StaffMessage;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.AdminDao;
import com.SuperemeAppealReporter.api.io.entity.CaseEntity;
import com.SuperemeAppealReporter.api.io.entity.CityEntity;
import com.SuperemeAppealReporter.api.io.entity.CountryEntity;
import com.SuperemeAppealReporter.api.io.entity.PostEntity;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.StateEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.UserSubscriptionDetailEntity;
import com.SuperemeAppealReporter.api.io.repository.CaseRepository;
import com.SuperemeAppealReporter.api.io.repository.PaymentRepository;
import com.SuperemeAppealReporter.api.io.repository.PostRepository;
import com.SuperemeAppealReporter.api.io.repository.UserRepository;
import com.SuperemeAppealReporter.api.io.repository.UserSubscriptionDetailRepository;
import com.SuperemeAppealReporter.api.pojo.Mail;
import com.SuperemeAppealReporter.api.pojo.StaffMail;
import com.SuperemeAppealReporter.api.service.AdminService;
import com.SuperemeAppealReporter.api.service.MasterService;
import com.SuperemeAppealReporter.api.service.NotificationService;
import com.SuperemeAppealReporter.api.service.RoleService;
import com.SuperemeAppealReporter.api.shared.dto.CityDto;
import com.SuperemeAppealReporter.api.shared.dto.ClientDto;
import com.SuperemeAppealReporter.api.shared.dto.CountryDto;
import com.SuperemeAppealReporter.api.shared.dto.StaffDto;
import com.SuperemeAppealReporter.api.shared.dto.StateDto;
import com.SuperemeAppealReporter.api.shared.util.AppUtility;
import com.SuperemeAppealReporter.api.ui.model.request.PostRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdatePostRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetCourtDropDownResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetOrderResponse;
import com.SuperemeAppealReporter.api.ui.model.response.GetPostResponse;
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MasterService masterService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private UserSubscriptionDetailRepository userSubscriptionDetailRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
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
			
			
			CountryDto country = new CountryDto();
			country.setId(userEntity.getAddressEntity().getCountryEntity().getId());
			country.setLabel(userEntity.getAddressEntity().getCountryEntity().getName());
			country.setValue(userEntity.getAddressEntity().getCountryEntity().getName());
			
			StateDto state = new StateDto();
			state.setId(userEntity.getAddressEntity().getStateEntity().getId());
			state.setLabel(userEntity.getAddressEntity().getStateEntity().getName());
			state.setValue(userEntity.getAddressEntity().getStateEntity().getName());
		
			
			CityDto city = new CityDto();
			city.setId(userEntity.getAddressEntity().getCityEntity().getId());
			city.setLabel(userEntity.getAddressEntity().getCityEntity().getName());
			city.setValue(userEntity.getAddressEntity().getCityEntity().getName());
			
			clientDto.setCountry(country);
			clientDto.setState(state);
			clientDto.setCity(city);
			clientDto.setPassword(userEntity.getPassword());
			clientDto.setZipCode(userEntity.getAddressEntity().getZipcode());
		    clientDto.setAddress(userEntity.getAddressEntity().getAddress());
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
			if(userEntity.isStaffActive())
			{
			   staffDto.setStaffActive(true);	
			}
			else
			{
				staffDto.setStaffActive(false);
			}
			staffDto.setStaffId(userEntity.getClientId());
			staffDto.setStaffCategory(userEntity.getUserType());
		    GetCourtDropDownResponse getCourtDropDownResponse = new GetCourtDropDownResponse();
		    getCourtDropDownResponse.setValue(userEntity.getUserType());
		    getCourtDropDownResponse.setLabel(userEntity.getUserType());
		    getCourtDropDownResponse.setId(userEntity.getRoleEntityList().get(0).getId());
		    staffDto.setRoleDropDownResponse(getCourtDropDownResponse);
		    
			CountryDto country = new CountryDto();
			country.setId(userEntity.getAddressEntity().getCountryEntity().getId());
			country.setLabel(userEntity.getAddressEntity().getCountryEntity().getName());
			country.setValue(userEntity.getAddressEntity().getCountryEntity().getName());
			
			StateDto state = new StateDto();
			state.setId(userEntity.getAddressEntity().getStateEntity().getId());
			state.setLabel(userEntity.getAddressEntity().getStateEntity().getName());
			state.setValue(userEntity.getAddressEntity().getStateEntity().getName());
		
			
			CityDto city = new CityDto();
			city.setId(userEntity.getAddressEntity().getCityEntity().getId());
			city.setLabel(userEntity.getAddressEntity().getCityEntity().getName());
			city.setValue(userEntity.getAddressEntity().getCityEntity().getName());
			staffDto.setStaffCategory(userEntity.getUserType());
			staffDto.setStaffId(userEntity.getClientId());
			staffDto.setCountry(country);
			staffDto.setState(state);
			staffDto.setCity(city);
			staffDto.setZipcode(userEntity.getAddressEntity().getZipcode());
			staffDto.setPassword(userEntity.getPassword());
			staffDto.setAddress(userEntity.getAddressEntity().getAddress());
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
	public CommonMessageResponse deleteStaff(DeleteStaffBo deleteStaffBo) {
		
		CommonMessageResponse deleteResponse = new CommonMessageResponse();
		try
		{
		 
		int id = Integer.parseInt(deleteStaffBo.getStaffId());
		// Checking if staff exists or not
		UserEntity userEntity = adminDao.findStaffById(id).orElseThrow(() -> new AppException(ErrorConstant.InvalidStaffIdError.ERROR_TYPE,
					ErrorConstant.InvalidStaffIdError.ERROR_CODE,
					ErrorConstant.InvalidStaffIdError.ERROR_MESSAGE));
		// Checking if requested id is belong to only staff users or not
		if(userEntity.getUserType().equals(UserType.USER.toString()))
		{
			throw new AppException(ErrorConstant.InvalidStaffIdError.ERROR_TYPE,
					ErrorConstant.InvalidStaffIdError.ERROR_CODE,
					ErrorConstant.InvalidStaffIdError.INVALID_STAFF_ERROR_MESSAGE);
		}
		
		// checking if staff is already deactivated
		if(!userEntity.getActive())
		{
			throw new AppException(ErrorConstant.InvalidStaffIdError.ERROR_TYPE,
					ErrorConstant.InvalidStaffIdError.ERROR_CODE,
					ErrorConstant.InvalidStaffIdError.STAFF_DEACTIVATED_ERROR_MESSAGE);
		}
		
		adminDao.deleteStaffById(userEntity.getId());
		
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
		
		deleteResponse.setMsg(StaffMessage.STAFF_DELETED);
		return deleteResponse;
	}

	@Transactional
	@Override
	public CommonMessageResponse updateStaff(UpdateStaffBo updateStaffBo) {
		
		CommonMessageResponse updateResponse = new CommonMessageResponse();
		int id = Integer.parseInt(updateStaffBo.getStaffId());
		
		try
		{
		
		// Fetching Staff record and checking if exist or  not
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
		userEntity.getAddressEntity().setAddress(updateStaffBo.getAddress());
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
		updateResponse.setMsg(StaffMessage.STAFF_UPDATED);
		return updateResponse;
	}

	@Transactional
	@Override
	public CommonMessageResponse deleteClient(DeleteClientBo deleteClientBo) {
		CommonMessageResponse deleteResponse = new CommonMessageResponse();
		try
		{
			int id = Integer.parseInt(deleteClientBo.getClientId());
			// checking if Client id exists or not
		UserEntity userEntity = adminDao.findStaffById(id).orElseThrow(() -> new AppException(ErrorConstant.InvalidClientIdError.ERROR_TYPE,
					ErrorConstant.InvalidClientIdError.ERROR_CODE,
					ErrorConstant.InvalidClientIdError.ERROR_MESSAGE));
		
		// Checking if client id belongs to only user or not
		if(!userEntity.getUserType().equals(UserType.USER.toString()))
		{
			throw new AppException(ErrorConstant.InvalidClientIdError.ERROR_TYPE,
					ErrorConstant.InvalidClientIdError.ERROR_CODE,
					ErrorConstant.InvalidClientIdError.INVALID_CLIENT_ERROR_MESSAGE);
		}
		
		// checking if Client is already deactivated
		if(!userEntity.getActive())
		{
			throw new AppException(ErrorConstant.InvalidClientIdError.ERROR_TYPE,
					ErrorConstant.InvalidClientIdError.ERROR_CODE,
					ErrorConstant.InvalidClientIdError.CLIENT_DEACTIVATED_ERROR_MESSAGE);
		}	
		adminDao.deleteClientById(userEntity.getId());
		
		}
		
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			String errorMessage = "Error in AdminServiceImpl --> deleteClient()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		deleteResponse.setMsg(ClientMessage.CLIENT_DELETED);
		return deleteResponse;
	}

	@Transactional
	@Override
	public CommonMessageResponse updateClient(UpdateClientBo updateClientBo) {

		
		CommonMessageResponse updateResponse = new CommonMessageResponse();
		int id = Integer.parseInt(updateClientBo.getClientId());
		
		try
		{
		UserEntity userEntity = adminDao.findStaffById(id).orElseThrow(() -> new AppException(ErrorConstant.InvalidClientIdError.ERROR_TYPE,
				ErrorConstant.InvalidClientIdError.ERROR_CODE,
				ErrorConstant.InvalidClientIdError.ERROR_MESSAGE));
		
		CountryEntity countryEntity = masterService.getCountryEntityByCountryId(updateClientBo.getCountryId());
		StateEntity stateEntity = masterService.getStateEntityByStateId(updateClientBo.getStateId());
		CityEntity cityEntity = masterService.getCityEntityByCityId(updateClientBo.getCityId());
		RoleEntity roleEntity = roleService.findByRoleId(updateClientBo.getRoleId());
		
		userEntity.setName(updateClientBo.getName());
		userEntity.setMobile(updateClientBo.getMobile());
		userEntity.setPassword(updateClientBo.getPassword());
		userEntity.setDesgination(updateClientBo.getDesgination());
		userEntity.setUserType(roleEntity.getName());
		userEntity.getAddressEntity().setCountryEntity(countryEntity);
		userEntity.getAddressEntity().setStateEntity(stateEntity);
		userEntity.getAddressEntity().setCityEntity(cityEntity);
		userEntity.getAddressEntity().setZipcode(updateClientBo.getZipCode());
		userEntity.getAddressEntity().setAddress(updateClientBo.getAddress());
		/** Assigning Role to User **/
		ArrayList<RoleEntity> roleList = new ArrayList<RoleEntity>();
		roleList.add(roleEntity);
		
		userEntity.setRoleEntityList(roleList);
		
		
		/** Creating OnBoardingMail object **/
		Mail onBoardingMail = new Mail();
		onBoardingMail.setBelongsTo(UserType.USER);
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
			notificationService.sendEmailNotification(onBoardingMail,updateFlag);
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
			String errorMessage = "Error in AdminServiceImpl --> updateClient()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		updateResponse.setMsg(ClientMessage.CLIENT_UPDATED);
		return updateResponse;
	
	}

	@Override
	public CommonPaginationResponse searchClient(SearchClientBo searchClientBo, int pageNumber, int perPageLimit) {
		
		String clientNameOrId = searchClientBo.getClientNameOrId();	
		String clientCategory = searchClientBo.getClientCategory();
		


		CommonPaginationResponse commonPaginationResponse = null;
		try
		{
				
		/**This if block is executed if the user category is different**/
		if(! (clientCategory.equals("ACTIVE") || clientCategory.equals("INACTIVE") || clientCategory.equals("ALL")))
        {
			throw new AppException(ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_TYPE,
					ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_CODE,
					ErrorConstant.InvalidClientCategoryForGetClientListError.ERROR_MESSAGE);
		}
		
		String userType = UserType.USER.toString();
		

		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPageLimit);
		Page<UserEntity> userEntityPage = null;
		List<Integer> subsriptionTypeList = new ArrayList<Integer>();
		if(clientCategory.equals("ALL"))
		{
			subsriptionTypeList.add(0);
			subsriptionTypeList.add(1);
			
		}
		else if(clientCategory.equals("ACTIVE"))
		{
			subsriptionTypeList.add(1);
		}
		else if(clientCategory.equals("INACTIVE"))
		{
			subsriptionTypeList.add(0);
		}
		
		userEntityPage = adminDao.getUserEntityPageByUserTypeAndSubscriptionTypeAndByClientNameOrId(pageableRequest, userType,clientNameOrId,subsriptionTypeList);
		List<UserEntity> userEntityList = userEntityPage.getContent();
		
		/**converting user entity list to client entity dto**/
		List<ClientDto> clientDtoList = new ArrayList<ClientDto> ();
		for(UserEntity userEntity : userEntityList)
		{
			ClientDto clientDto = new ClientDto();
			BeanUtils.copyProperties(userEntity, clientDto);
			
			
			CountryDto country = new CountryDto();
			country.setId(userEntity.getAddressEntity().getCountryEntity().getId());
			country.setLabel(userEntity.getAddressEntity().getCountryEntity().getName());
			country.setValue(userEntity.getAddressEntity().getCountryEntity().getName());
			
			StateDto state = new StateDto();
			state.setId(userEntity.getAddressEntity().getStateEntity().getId());
			state.setLabel(userEntity.getAddressEntity().getStateEntity().getName());
			state.setValue(userEntity.getAddressEntity().getStateEntity().getName());
		
			
			CityDto city = new CityDto();
			city.setId(userEntity.getAddressEntity().getCityEntity().getId());
			city.setLabel(userEntity.getAddressEntity().getCityEntity().getName());
			city.setValue(userEntity.getAddressEntity().getCityEntity().getName());
			
			clientDto.setCountry(country);
			clientDto.setState(state);
			clientDto.setCity(city);
			clientDto.setPassword(userEntity.getPassword());
			clientDto.setZipCode(userEntity.getAddressEntity().getZipcode());
		
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
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> searchClient()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		return commonPaginationResponse;
	
		
		
		
	}

	@Override
	public CommonPaginationResponse searchStaff(SearchStaffBo searchStaffBo, int pageNumber, int perPageLimit) {

		
		String clientNameOrId = searchStaffBo.getStaffNameOrId();	
		String staffCategory = searchStaffBo.getStaffCategory();
		CommonPaginationResponse commonPaginationResponse = null;
		try
		{
			List<String> userTypeList = new ArrayList<String>();
		String userTypeAdmin = UserType.ADMIN.toString();
		String userTypeOperator = UserType.DATA_ENTRY_OPERATOR.toString();
		String userTypeSuperAdmin = UserType.SUPER_ADMIN.toString();
		
		
		if(staffCategory.equals("ALL"))
		{
			userTypeList.add(userTypeSuperAdmin);
			userTypeList.add(userTypeOperator);
			userTypeList.add(userTypeAdmin);
			
		}
		else
		{
			userTypeList.add(staffCategory);
		}
		
		
		
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPageLimit);
		Page<UserEntity> userEntityPage = null;
		
		
		userEntityPage = adminDao.getUserEntityPageByUserTypeAndByStaffNameOrId(pageableRequest,clientNameOrId,userTypeList);
		List<UserEntity> userEntityList = userEntityPage.getContent();
		
		/**converting user entity list to staff entity dto**/
		List<StaffDto> staffDtoList = new ArrayList<StaffDto> ();
		for(UserEntity userEntity : userEntityList)
		{
			StaffDto staffDto = new StaffDto();
			BeanUtils.copyProperties(userEntity, staffDto);
			
			
			CountryDto country = new CountryDto();
			country.setId(userEntity.getAddressEntity().getCountryEntity().getId());
			country.setLabel(userEntity.getAddressEntity().getCountryEntity().getName());
			country.setValue(userEntity.getAddressEntity().getCountryEntity().getName());
			
			StateDto state = new StateDto();
			state.setId(userEntity.getAddressEntity().getStateEntity().getId());
			state.setLabel(userEntity.getAddressEntity().getStateEntity().getName());
			state.setValue(userEntity.getAddressEntity().getStateEntity().getName());
		
			
			CityDto city = new CityDto();
			city.setId(userEntity.getAddressEntity().getCityEntity().getId());
			city.setLabel(userEntity.getAddressEntity().getCityEntity().getName());
			city.setValue(userEntity.getAddressEntity().getCityEntity().getName());
			
			staffDto.setStaffCategory(userEntity.getUserType());
			staffDto.setStaffId(userEntity.getClientId());
			staffDto.setCountry(country);
			staffDto.setState(state);
			staffDto.setCity(city);
			staffDto.setPassword(userEntity.getPassword());
			staffDto.setZipcode(userEntity.getAddressEntity().getZipcode());
		
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
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> searchStaff()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		return commonPaginationResponse;
	
		
		
		
	
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public CommonMessageResponse staffActiveInactive(boolean status,String staffId) {
		
		CommonMessageResponse  commonMessageResponse = null;
		try
		{
		// String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			
		 UserEntity userEntity = userRepository.getUserByStaffId(Integer.parseInt(staffId));
		 userEntity.setStaffActive(status);
		 commonMessageResponse = new CommonMessageResponse();
		 String embedMessage = "DEACTIVATED";
		 
		 if(status)
		 {
			 embedMessage = "ACTIVATED";
		 }
		 commonMessageResponse.setMsg("Staff status successfully "+embedMessage);
		
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> staffActiveInactive()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		 return commonMessageResponse;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public CommonMessageResponse caseOveruledStatusChange(boolean status, String docId) {
	
		CommonMessageResponse  commonMessageResponse = null;
		try
		{
		CaseEntity caseEntity = caseRepository.findByDocId(Long.parseLong(docId));
		
		caseEntity.setOverruled(status);
		 commonMessageResponse = new CommonMessageResponse();
		 String embedMessage = "DEACTIVATED";
		 
		 if(status)
		 {
			 embedMessage = "ACTIVATED";
		 }
		 commonMessageResponse.setMsg("Case Overuled "+embedMessage);
		
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> caseOveruledStatusChange()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		 return commonMessageResponse;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public CommonMessageResponse caseLiveStatusChange(boolean status, String docId) {
		CommonMessageResponse  commonMessageResponse = null;
		try
		{
			CaseEntity caseEntity = caseRepository.findByDocId(Long.parseLong(docId));
			
			caseEntity.setLive(status);
		commonMessageResponse = new CommonMessageResponse();
		 String embedMessage = "DEACTIVATED";
		 
		 if(status)
		 {
			 embedMessage = "ACTIVATED";
		 }
		 commonMessageResponse.setMsg("Case Live Status "+embedMessage);
		
		}
		catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> caseLiveStatusChange()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		
		 return commonMessageResponse;
	}

	@Override
	@org.springframework.transaction.annotation.Transactional
	public CommonPaginationResponse getOrderList(int pageNumber, int perPageLimit) {

     CommonPaginationResponse commonPaginationResponse = null;
     try
     {
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, perPageLimit);
		Page<UserSubscriptionDetailEntity> userSubscriptionPage = null;
		
		userSubscriptionPage = userSubscriptionDetailRepository.findAll(pageableRequest);
		
		List<UserSubscriptionDetailEntity> userSubscriptionDetailEntityList = userSubscriptionPage.getContent();
		
		List<GetOrderResponse> getOrderResponseList = new ArrayList<>();
		
		for(UserSubscriptionDetailEntity entity : userSubscriptionDetailEntityList)
		{
			String paymentId = entity.getPaymentEntity().getPayment_id();
			String orderId = entity.getPaymentEntity().getTransaction_id();
			double amount = entity.getPaymentEntity().getAmount();
			String planName = entity.getSubscriptionPlanEntity().getSubscriptionName();
			Date orderDate = entity.getPaymentEntity().getCreatedDate();
			Date planStartDate  = entity.getStartDate();
			Date planEndDate = entity.getEndDate();
			String clientId = entity.getUserEntity().getClientId()+"";
			String status  = entity.getPaymentEntity().getPaymentStatus()+"";
			
			GetOrderResponse getOrderResponse = new GetOrderResponse();
			getOrderResponse.setAmount(amount);
			getOrderResponse.setClientId(clientId);
			getOrderResponse.setOrderDate(AppUtility.changeDateFormatToOnlyDate(orderDate));
			getOrderResponse.setOrderId(orderId);
			getOrderResponse.setOrderTime(AppUtility.changeDateFormatToOnlyTime(orderDate));
			getOrderResponse.setPaymentId(paymentId);
			getOrderResponse.setPlanEndDate(AppUtility.changeDateFormatToOnlyDate(planEndDate));
			getOrderResponse.setPlanName(planName);
			getOrderResponse.setPlanStartDate(AppUtility.changeDateFormatToOnlyDate(planStartDate));
			getOrderResponse.setStatus(status);
			
			getOrderResponseList.add(getOrderResponse);
		}
		
		commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userSubscriptionPage.getTotalPages());
		commonPaginationResponse.setOjectList(getOrderResponseList);
		
	}
     catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> getOrderList()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
     return commonPaginationResponse;
}

	@Override
	public CommonMessageResponse makePost(PostRequest postRequest) {
		
		CommonMessageResponse commonMessageResponse = null;
		
		try
		{
		PostEntity postEntity = new PostEntity();
		postEntity.setBody(postRequest.getPostBody());
		postEntity.setHeading(postRequest.getPostHeading());
		postEntity.setLinkedDocId(postRequest.getLinkedDocId());
		postRepository.save(postEntity);
		
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg("Post saved Successfully");
		
		return commonMessageResponse;
		
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> makePost()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
		}
	}
	
	

	@Override
	@Transactional
	public CommonMessageResponse updatePost(UpdatePostRequest postRequest) {
		
		CommonMessageResponse commonMessageResponse = null;
		
		try
		{
		PostEntity postEntity = postRepository.findById(Integer.parseInt(postRequest.getId())).get();
		postEntity.setBody(postRequest.getBody());
		postEntity.setHeading(postRequest.getHeading());
		postEntity.setLinkedDocId(postRequest.getLinkedDocId());
		
		
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg("Post updated Successfully");
		
		return commonMessageResponse;
		
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> updatePost()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
		}
	}

	@Override
	public CommonPaginationResponse getPost(int pageNumber, int pagePerLimit) {
		CommonPaginationResponse	commonPaginationResponse = null;
		
		try {
			
		if (pageNumber > 0)
			pageNumber = pageNumber - 1;
		
		Pageable pageableRequest = PageRequest.of(pageNumber, pagePerLimit);
		Page<Object> posPage = null;
		
		List<GetPostResponse> getPostList = new ArrayList<GetPostResponse>();
		posPage = postRepository.getPostList(pageableRequest);
		
		
		List<Object> list = posPage.getContent();
		
		for(int i=0;i<list.size();i++){
			Object arr[] = (Object[])list.get(i);
			
			String id = String.valueOf(arr[0]);
			String body = String.valueOf(arr[1]);
			String head = String.valueOf(arr[2]);
			String linkedDocId = String.valueOf(arr[3]);
			GetPostResponse getPostResponse = new GetPostResponse();
			getPostResponse.setId(id);
			getPostResponse.setBody(body);
			getPostResponse.setHead(head);
			getPostResponse.setLinkedDocId(linkedDocId);
			
			getPostList.add(getPostResponse);
			
			
			
		}
		
		commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(posPage.getTotalPages());
		commonPaginationResponse.setOjectList(getPostList);
		
	}
     catch(AppException appException)
		{
			throw appException;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> getPost()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
     return commonPaginationResponse;
		
	}

	@Override
	public CommonMessageResponse deletePost(String postId) {
		
		CommonMessageResponse commonMessageResponse = null;
		try
		{
		postRepository.deleteById(Integer.parseInt(postId));
		commonMessageResponse = new CommonMessageResponse();
		commonMessageResponse.setMsg("Post Deleted Successfully");
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			String errorMessage = "Error in AdminServiceImpl --> deletePost()";
			AppException appException = new AppException("Type : " + ex.getClass()
			+ ", " + "Cause : " + ex.getCause() + ", " + "Message : " + ex.getMessage(),ErrorConstant.InternalServerError.ERROR_CODE,
					ErrorConstant.InternalServerError.ERROR_MESSAGE + " : " + errorMessage);
			throw appException;
			
		}
		
		return commonMessageResponse;
	}
	
}
