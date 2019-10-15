package com.SuperemeAppealReporter.api.converter;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;

import com.SuperemeAppealReporter.api.bo.AddStaffBo;
import com.SuperemeAppealReporter.api.bo.DeleteClientBo;
import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.UserSignupBo;
import com.SuperemeAppealReporter.api.ui.model.request.AddClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.AddStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteClientRequest;
import com.SuperemeAppealReporter.api.ui.model.request.DeleteStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdateStaffRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetClientListRequest;
import com.SuperemeAppealReporter.api.ui.model.request.GetStaffListRequest;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
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
	
	public static DeleteStaffBo convertDeleteStaffRequestToDeleteStaffBo(DeleteStaffRequest deleteStaffRequest) {
		DeleteStaffBo deleteStaffBo = new DeleteStaffBo();
		BeanUtils.copyProperties(deleteStaffRequest, deleteStaffBo);
		return deleteStaffBo;
	}

	public static UpdateStaffBo convertUpdateStaffRequestToUpdateStaffBo(UpdateStaffRequest updateStaffRequest) {
		UpdateStaffBo updateStaffBo = new UpdateStaffBo();
		BeanUtils.copyProperties(updateStaffRequest, updateStaffBo);
		return updateStaffBo;
}

	public static DeleteClientBo convertDeleteClientRequestToDeleteClientBo(DeleteClientRequest deleteClientRequest) {
		DeleteClientBo deleteClientBo = new DeleteClientBo();
		BeanUtils.copyProperties(deleteClientRequest, deleteClientBo);
		return deleteClientBo;
	}
}