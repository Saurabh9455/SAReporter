package com.SuperemeAppealReporter.api;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SuppressWarnings("deprecation")
@SpringBootApplication
@EnableScheduling
public class SuperemeAppealReporterApplication extends SpringBootServletInitializer  {


	public static void main(String[] args) {
		SpringApplication.run(SuperemeAppealReporterApplication.class, args);
		
	}

	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(SuperemeAppealReporterApplication.class);
	    }
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedHeaders(new String[]{"*"}).allowedMethods(new String[]{"PUT","POST","DELETE","GET"});
	            
	        }
	    };
	    
	   
	}
	 @PostConstruct
	    public void init(){
	        TimeZone.setDefault(TimeZone.getTimeZone("IST"));   // It will set UTC timezone
	        System.out.println("Spring boot application running in UTC timezone :"+new Date());   // It will print UTC timezone
	    }

	
}
