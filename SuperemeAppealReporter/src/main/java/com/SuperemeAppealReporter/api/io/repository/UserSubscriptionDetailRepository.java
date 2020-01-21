package com.SuperemeAppealReporter.api.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.SuperemeAppealReporter.api.io.entity.UserSubscriptionDetailEntity;

@Repository
public interface UserSubscriptionDetailRepository extends PagingAndSortingRepository<UserSubscriptionDetailEntity, Integer> {

@Query(value="select * from user_subscription_detail where user_entity_id=?1 and (is_plan_active =?2 OR is_plan_active=0) and DATE(end_date)>= DATE(now())",nativeQuery=true)
public List<UserSubscriptionDetailEntity> findByUserId(int userId,boolean isPlanActive);

	
}
