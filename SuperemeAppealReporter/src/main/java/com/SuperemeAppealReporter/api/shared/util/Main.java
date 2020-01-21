package com.SuperemeAppealReporter.api.shared.util;

import java.util.Random;

public class Main {
	

	     public static void main(String []args){
	        
		    Random r = new Random( System.currentTimeMillis() );
		    int k= ((1 + r.nextInt(2)) * 100000 + r.nextInt(1000));
		    
		    System.out.println(k);
		
	     
	}
}
