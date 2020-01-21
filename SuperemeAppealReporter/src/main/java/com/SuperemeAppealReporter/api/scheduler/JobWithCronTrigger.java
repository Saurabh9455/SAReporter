
package com.SuperemeAppealReporter.api.scheduler;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.SuperemeAppealReporter.api.service.SubscriptionPlanStatusProcessor;




/**
 * @author pavan.solapure
 *
 */
@Component
@DisallowConcurrentExecution

public class JobWithCronTrigger implements Job {

	


	@Autowired
    private SubscriptionPlanStatusProcessor subscriptionPlanStatusProcessor;
	


	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
	
		subscriptionPlanStatusProcessor.checkAndUpdatePlanStatus();
	}

/*	@Bean(name = "jobWithCronTriggerBean")
	public JobDetail sampleJob() {
		return ConfigureQuartz.createJobDetail().getObject();
		
	
	}*/	

	/*@Bean(name = "jobWithCronTriggerBeanTrigger")
	public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithCronTriggerBean") JobDetail jobDetail) {
		frequency = "0/7 * * * * ?";
		return ConfigureQuartz.createCronTrigger(jobDetail, frequency);
		
		
	}*/
}

 