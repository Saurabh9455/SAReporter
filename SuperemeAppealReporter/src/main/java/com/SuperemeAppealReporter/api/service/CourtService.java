package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.AddCourtBo;
import com.SuperemeAppealReporter.api.bo.AddCourtBranchBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBo;
import com.SuperemeAppealReporter.api.bo.DeleteCourtBranchBo;
import com.SuperemeAppealReporter.api.bo.GetCourtBo;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

public interface CourtService {

	public CommonMessageResponse addCourt(AddCourtBo addCourtBo);

	public CommonMessageResponse deleteCourt(DeleteCourtBo deleteCourtBo);

	public CommonMessageResponse addCourtBranch(AddCourtBranchBo addCourtBranchBo);

	public CommonMessageResponse deleteCourtBranch(DeleteCourtBranchBo deleteCourtBranchBo);

	public CommonPaginationResponse getCourtService(GetCourtBo getCourtBo, int pageNumber, int perPage);
	
	public CommonPaginationResponse getCourtServiceV2(GetCourtBo getCourtBo, int pageNumber, int perPage);

}
