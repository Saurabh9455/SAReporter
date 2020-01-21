package com.SuperemeAppealReporter.api.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SuperemeAppealReporter.api.io.repository.MyTableRepository;
import com.SuperemeAppealReporter.api.service.SubscriptionPlanStatusProcessor;

@Service
public class SubscriptionPlanStatusProcessorImpl implements SubscriptionPlanStatusProcessor{

	@Autowired
	MyTableRepository myTableRepository;
	
	@Override
	public boolean checkAndUpdatePlanStatus() {
		
		System.out.println("FIRED AT "+new Date());
		
		myTableRepository.updateUserPlanStatus();
		return true;
	}

}
