package com.SuperemeAppealReporter.api.converter;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.ui.model.request.AddClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.AddStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetClientListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetStaffListRequest;

public class AdminConverter {

	public static GetClientListBo convertGetClientListRequestToGetClientListResponse(GetClientListRequest getClientListRequest)
	{
		GetClientListBo getClientListBo = new GetClientListBo();
		BeanUtils.copyProperties(getClientListRequest, getClientListBo);
		return getClientListBo;
	}
	
	public static UserSignupBo convertAddClientRequestToUserSignupBo(AddClientRequest addClientRequest)
	{
		UserSignupBo userSignupBo = new UserSignupBo();
		BeanUtils.copyProperties(addClientRequest, userSignupBo);
		return userSignupBo;
	}

	public static GetStaffListBo convertGetStaffListRequestToGetStaffListResponse(GetStaffListRequest getStaffListRequest) {
		GetStaffListBo getStaffListBo = new GetStaffListBo();
		BeanUtils.copyProperties(getStaffListRequest, getStaffListBo);
		return getStaffListBo;
	}

	public static AddStaffBo convertAddStaffRequestToAddStaffBo(AddStaffRequest addStaffRequest) {
		AddStaffBo addStaffBo = new AddStaffBo();
		BeanUtils.copyProperties(addStaffRequest, addStaffBo);
		return addStaffBo;
	}
}
