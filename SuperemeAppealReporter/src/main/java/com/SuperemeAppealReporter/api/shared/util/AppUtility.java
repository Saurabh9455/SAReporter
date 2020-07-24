package com.SuperemeAppealReporter.api.shared.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.SuperemeAppealReporter.api.io.entity.UserEntity;
import com.SuperemeAppealReporter.api.io.entity.VerificationTokenEntity;

public class AppUtility {

	public static VerificationTokenEntity generateVerificationToken(UserEntity employerEntity)
	{
		VerificationTokenEntity verificationTokenEntity = new VerificationTokenEntity(employerEntity);
		return verificationTokenEntity;
	
	}
	
	public static  String generateRandomString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	public static String changeDateFormatToOnlyDate(Date date)
	{
		 
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String strDate= formatter.format(date);  
	    return strDate;
	}
	public static boolean isObjectEmpty(Object object) {
		if(object == null) return true;
		else if(object instanceof String) {
			if (((String)object).trim().length() == 0) {
				return true;
			}
		} else if(object instanceof Collection) {
			return isCollectionEmpty((Collection<?>)object);
		}
		return false;
	}
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	public static String changeDateFormatToOnlyTime(Date date)
	{
		 
	    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");  
	    String strDate= formatter.format(date);  
	    return strDate;
	}
	
	public static int genClientId() {
	    Random r = new Random( System.currentTimeMillis() );
	    return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
	
	public static int genDocId() {
	    Random r = new Random( System.currentTimeMillis() );
	    return ((1 + r.nextInt(2)) * 100000 + r.nextInt(10000));
	}
	
	public static Date convertStringToDate(String format,String dateStr) throws ParseException{
	   
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date outDate = sdf.parse(dateStr);
		
		return outDate;
	}
	
	public static String convertDateToString(String format,Date date) throws ParseException{
		   
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String outDate = sdf.format(date);
		
		return outDate;
	}
	
	
}
