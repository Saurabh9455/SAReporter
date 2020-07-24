package com.SuperemeAppealReporter.api.firebase;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationService {

	private static final String FIREBASE_SERVER_KEY = "AAAA3rnCmmM:APA91bFjOCIsWODOAEpzdyY0c54GqyLfJe56x9E4dxKY_Al11gW_eKhg2Iqr33Dl3dB8oh-coSzvWvtk7GhvuWXCXga3sNnLw3GiiCKLKk2TwrjGDShJvWwdw1CfXeDrozZbJ9Jsk1QU";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	 // @Async
	//  public CompletableFuture<String> send(HttpEntity<String> entity) {
	  public String send(HttpEntity<String> entity) {
	 
	    RestTemplate restTemplate = new RestTemplate();
	 
	    /**
	    https://fcm.googleapis.com/fcm/send
	    Content-Type:application/json
	    Authorization:key=FIREBASE_SERVER_KEY*/
	 
	    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
	    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
	    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
	    restTemplate.setInterceptors(interceptors);
	 
	    String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
	 
	    return firebaseResponse; 
	    		//CompletableFuture.completedFuture(firebaseResponse);
	  }
	
}
