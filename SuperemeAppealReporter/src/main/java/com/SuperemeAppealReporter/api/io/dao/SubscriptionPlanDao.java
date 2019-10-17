package com.SuperemeAppealReporter.api.io.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.SuperemeAppealReporter.api.io.entity.SubscriptionPlanEntity;

public interface SubscriptionPlanDao {

	public void savePlan(SubscriptionPlanEntity planEntity);

	public Optional<SubscriptionPlanEntity> findById(int planId);

	public void deletePlan(int planId);

	public Page<SubscriptionPlanEntity> getAllSubscriptionEntityPage(Pageable pageableRequest);
	
}
