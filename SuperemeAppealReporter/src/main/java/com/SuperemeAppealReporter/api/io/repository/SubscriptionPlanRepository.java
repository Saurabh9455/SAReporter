package com.SuperemeAppealReporter.api.io.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.SubscriptionPlanEntity;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlanEntity, Integer>{

	@Query(value = "select * from subscription_plan where is_active = 1 ",nativeQuery = true)
	Page<SubscriptionPlanEntity> getAllSubscriptionEntityPage(Pageable pageableRequest);

}