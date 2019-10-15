package com.SuperemeAppealReporter.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.constant.ErrorConstant;
import com.SuperemeAppealReporter.api.enums.UserType;
import com.SuperemeAppealReporter.api.exception.type.AppException;
import com.SuperemeAppealReporter.api.io.dao.AdminDao;
import com.SuperemeAppealReporter.api.io.entity.RoleEntity;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.service.AdminService;
import com.SuperemeAppealReporter.api.service.RoleService;
import com.SuperemeAppealReporter.api.shared.dto.ClientDto;
import com.SuperemeAppealReporter.api.shared.dto.StaffDto;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private RoleService roleService;
	@Override
	public CommonPaginationResponse getClientListResponseService(int pageNumber, int perPage, String userType, GetClientListBo getClientListBo) {

		
		
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
		
		CommonPaginationResponse commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userEntityPage.getTotalPages());
		commonPaginationResponse.setOjectList(clientDtoList);
		
		return commonPaginationResponse;
	}

	@Override
	public CommonPaginationResponse getStaffListResponseService(int pageNumber, int perPageLimit,
			GetStaffListBo getStaffListBo) {
		
		
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
		
		CommonPaginationResponse commonPaginationResponse = new CommonPaginationResponse();
		commonPaginationResponse.setTotalNumberOfPagesAsPerGivenPageLimit(userEntityPage.getTotalPages());
		commonPaginationResponse.setOjectList(staffDtoList);
		
		return commonPaginationResponse;
	}
}
