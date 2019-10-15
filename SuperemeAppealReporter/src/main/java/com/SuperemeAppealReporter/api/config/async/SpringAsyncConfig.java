package com.SuperemeAppealReporter.api.config.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
     
    @Override
    public Executor getAsyncExecutor() {
        
    	ThreadPoolTaskExecutor threadPoolTaskExecuter = new ThreadPoolTaskExecutor();
    	threadPoolTaskExecuter.setKeepAliveSeconds(60);
    	threadPoolTaskExecuter.setCorePoolSize(5);
    	threadPoolTaskExecuter.setMaxPoolSize(Integer.MAX_VALUE);
    	threadPoolTaskExecuter.setQueueCapacity(Integer.MAX_VALUE);
    	threadPoolTaskExecuter.setRejectedExecutionHandler( new ThreadPoolExecutor.CallerRunsPolicy());
    	
    	/**To initialize the properties set above **/
    	threadPoolTaskExecuter.initialize();
    	
    	return threadPoolTaskExecuter;
    
    }
     
}
