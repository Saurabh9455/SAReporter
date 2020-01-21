package com.SuperemeAppealReporter.api.service;

import com.SuperemeAppealReporter.api.bo.AddPlanBo;
import com.SuperemeAppealReporter.api.bo.DeletePlanBo;
import com.SuperemeAppealReporter.api.bo.GetPlanListBo;
import com.SuperemeAppealReporter.api.ui.model.request.EditPlan;
import com.SuperemeAppealReporter.api.ui.model.response.CommonMessageResponse;
import com.SuperemeAppealReporter.api.ui.model.response.CommonPaginationResponse;

public interface SubscriptionPlanService {

	CommonMessageResponse addPlan(AddPlanBo addPlanBo);

	CommonMessageResponse deletePlan(DeletePlanBo deletePlanBo);

	CommonPaginationResponse getPlanList(int pageNumber, int perPageLimit, GetPlanListBo getPlanListBo);

	CommonMessageResponse editPlan(EditPlan editPlan);
}
