package com.springMVC.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CommUtil {
     
 	private final static String regexEmail = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";	
	private final static String CONTENT_TYPE_PLAIN = "text/plain;charset=utf-8";
 	private final static String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
 	
public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDateTimeNow(){
		return sdf2.format(new Date());
	}
	
	public static String getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static String getDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static String getPreMonthTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(calendar.getTime());
		
	}
	
	public static String getTimeSDF2(){
		Date date = new Date();
		return sdf2.format(date);
	}
	
	public static String getPreMonthTimeSDF2(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		return sdf2.format(calendar.getTime());
		
	}
	
	public static String DataFormatTransfer(String Time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf2.parse(Time);
		String newTime;
		newTime = sdf.format(date);
		return newTime;
	}
 	 
 	public static boolean validateEmail(String email){
 		if (email.matches(regexEmail)){
 			return true;
 		}else{
 			return false;
 		}
 	 }     

	public static String getMessageContentType(String content) {
	    
	    if (StringUtils.isEmpty(content)) return CONTENT_TYPE_PLAIN;
	    if (content.indexOf("</p>") >= 0 || content.indexOf("</h") >= 0) return CONTENT_TYPE_HTML;
	    return CONTENT_TYPE_PLAIN;
	}
	
	public static int getDayOfWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}	

}

