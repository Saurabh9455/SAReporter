package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.DeleteClientBo;
import com.SuperemeAppealReporter.api.bo.DeleteStaffBo;
import com.SuperemeAppealReporter.api.bo.GetClientListBo;
import com.SuperemeAppealReporter.api.bo.GetStaffListBo;
import com.SuperemeAppealReporter.api.bo.SearchClientBo;
import com.SuperemeAppealReporter.api.bo.SearchStaffBo;
import com.SuperemeAppealReporter.api.bo.UpdateClientBo;
import com.SuperemeAppealReporter.api.bo.UpdateStaffBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;
import com.SuperemeAppealReporter.api.ui.model.request.PostRequest;
import com.SuperemeAppealReporter.api.ui.model.request.UpdatePostRequest;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;

public interface AdminService {

	public CommonPaginationResponse getClientListResponseService(int pageNumber, int perPage, String userType,
			GetClientListBo getClientListBo);

	public CommonPaginationResponse getStaffListResponseService(int pageNumber, int perPageLimit,
			GetStaffListBo getStaffListBo);
	
	public CommonMessageResponse deleteStaff(DeleteStaffBo deleteStaffBo);

	public CommonMessageResponse updateStaff(UpdateStaffBo updateStaffBo);

	public CommonMessageResponse deleteClient(DeleteClientBo deleteClientBo);

	public CommonMessageResponse updateClient(UpdateClientBo updateClientBo);

	public CommonPaginationResponse searchClient(SearchClientBo searchClientBo, int pageNumber, int perPageLimit);

	public CommonPaginationResponse searchStaff(SearchStaffBo searchStaffBo, int pageNumber, int perPageLimit);

	public CommonMessageResponse staffActiveInactive(boolean status,String staffId);
	
	public CommonMessageResponse caseOveruledStatusChange(boolean status,String docId);
	
	public CommonMessageResponse caseLiveStatusChange(boolean status,String docId);
	
	public CommonPaginationResponse getOrderList(int pageNumber, int perPageLimit);
	
	public CommonMessageResponse makePost(PostRequest postReqest);
	

	CommonMessageResponse updatePost(UpdatePostRequest postRequest);
	
	CommonPaginationResponse getPost(int pageNumber, int pagePerLimit);
	
	CommonMessageResponse deletePost(String postId);
}


