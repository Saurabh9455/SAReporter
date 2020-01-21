

package com.SuperemeAppealReporter.api.config.quartz;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.SuperemeAppealReporter.api.scheduler.JobWithCronTrigger;


/**
 * @author pavan.solapure
 *
 */

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class ConfigureQuartz {

	/*@Autowired
	List<Trigger> listOfTrigger;*/

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(Trigger trigger,DataSource dataSource, JobFactory jobFactory) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);
		factory.setAutoStartup(true);
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);
		
		factory.setQuartzProperties(quartzProperties());
		factory.setTriggers(trigger);
		
		// Here we will set all the trigger beans we have defined.
	/*	if (!AppUtility.isObjectEmpty(listOfTrigger)) {
			factory.setTriggers(listOfTrigger.toArray(new Trigger[listOfTrigger.size()]));
		}*/
		

		return factory;
	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}



	// Use this method for creating cron triggers instead of simple triggers:
	@Bean
	public  CronTriggerFactoryBean createCronTrigger() {
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		
		factoryBean.setJobDetail(createJobDetail().getObject());
		factoryBean.setCronExpression("0 0 1 * * ?");
	
		factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
	return factoryBean;
		
	
	}


	@Bean
	public  JobDetailFactoryBean createJobDetail() {

        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
       
        jobDetailFactory.setJobClass(JobWithCronTrigger.class);
     
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
	}

}



