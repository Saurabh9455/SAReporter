package com.SuperemeAppealReporter.api.converter;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.ForgetPasswordBo;
import com.SuperemeAppealReporter.api.bo.ResetPasswordBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.shared.dto.UserDto;
import com.SuperemeAppealReporter.api.ui.model.request.ForgetPasswordRequest;
import com.SuperemeAppealReporter.api.ui.model.request.ResetPasswordRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UserSignupRequest;

public class UserConverter {

	public static UserDto convertUserEntityToUserDto(UserEntity userEntity)
	{ 
		 
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;

	}
	
	public static UserSignupBo convertUserSignupRequestToUserSignupBo(UserSignupRequest userSignupRequest)
	{
		UserSignupBo userSignupBo = new UserSignupBo();
	    BeanUtils.copyProperties(userSignupRequest, userSignupBo);
	    return userSignupBo;
	}
	
	public static UserEntity convertUserSignupBoToUserEntity(UserSignupBo userSignupBo)
	{
		UserEntity userEntity = new UserEntity();
	    BeanUtils.copyProperties(userSignupBo, userEntity);
	    return userEntity;
	}

	/*
	 * private static <T extends Object>T convert(Object sourceObject,Class<T>
	 * targetType) { if(targetType.getName().equals(
	 * "com.SuperemeAppealReporter.api.shared.dto.UserDto")) {
	 * 
	 * } BeanUtils.copyProperties(sourceObject, targetObject); return
	 * targetType.cast(targetObject); }
	 */
	
	public static ForgetPasswordBo convertForgertPasswordRequestToForgetPasswordBo(ForgetPasswordRequest forgetPasswordRequest)
	{
		ForgetPasswordBo forgetPasswordBo = new ForgetPasswordBo();
	    BeanUtils.copyProperties(forgetPasswordRequest, forgetPasswordBo);
	    return forgetPasswordBo;
	}
	
	public static ResetPasswordBo convertResetPasswordRequestToResetPasswordBo(ResetPasswordRequest resetPasswordRequest)
	{
		ResetPasswordBo resetPasswordBo = new ResetPasswordBo();
	    BeanUtils.copyProperties(resetPasswordRequest, resetPasswordBo);
	    return resetPasswordBo;
	}

	public static UserEntity convertAddStaffBoToUserEntity(AddStaffBo addStaffBo) {
		UserEntity userEntity = new UserEntity();
	    BeanUtils.copyProperties(addStaffBo, userEntity);
	    return userEntity;
	}
	
	
}
