package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

public interface AdminService {

	public CommonPaginationResponse getClientListResponseService(int pageNumber, int perPage, String userType,
			GetClientListBo getClientListBo);

	public CommonPaginationResponse getStaffListResponseService(int pageNumber, int perPageLimit,
			GetStaffListBo getStaffListBo);
}
