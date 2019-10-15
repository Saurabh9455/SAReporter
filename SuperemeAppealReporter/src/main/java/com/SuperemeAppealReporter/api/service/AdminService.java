package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.DeleteClientBo;
import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

public interface AdminService {

	public CommonPaginationResponse getClientListResponseService(int pageNumber, int perPage, String userType,
			GetClientListBo getClientListBo);

	public CommonPaginationResponse getStaffListResponseService(int pageNumber, int perPageLimit,
			GetStaffListBo getStaffListBo);
	
	public CommonMessageResponse deleteStaff(DeleteStaffBo deleteStaffBo);

	public CommonMessageResponse updateStaff(UpdateStaffBo updateStaffBo);

	public CommonMessageResponse deleteClient(DeleteClientBo deleteClientBo);
}
