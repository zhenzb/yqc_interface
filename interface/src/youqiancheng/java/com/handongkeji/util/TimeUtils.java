package com.handongkeji.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH");
	
	public static void main(String[] args) {
		System.out.println(TimeUtils.dateMonthPoints(new Date()));
	}
	
	// Date 转 String
	public static String dateMonthPoints(Date dTime) { 
		String sTime = null; 
		try { 
			sTime = sdf3.format(dTime);
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return sTime; 
	} 

	// Date 转 String
	public static String dateMonth(Date dTime) { 
		String sTime = null; 
		try { 
			sTime = sdf1.format(dTime); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return sTime; 
	} 
	
	// String 转 Date
	public static Date strToDat(String sTime) {
		Date dTime = null;
		try {
			dTime = sdf.parse(sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dTime;
	}
	
	public static Date strToBirthday(String sTime) {
		Date dTime = null;
		try {
			dTime = sdf1.parse(sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dTime;
	}
	
	public static Date dateNotSS(String sTime) {
		Date dTime = null;
		try {
			dTime = sdf2.parse(sTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dTime;
	}

	// Date 转 String
	public static String datToStr(Date dTime) {
		String sTime = null;
		try {
			sTime = sdf.format(dTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sTime;
	}
	// Date 转 String
	public static String datToStr1(Date dTime) {
		String sTime = null;
		try {
			sTime = sdf1.format(dTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sTime;
	}

	// Long 转 Date
	public static Date lonToDat(Long lTime) {
		Date dTime = new Date(lTime);
		return dTime;
	}

	// String 转 Long
	public static Long datToLon(String dTime) throws Exception {
		Long lTime = sdf.parse(dTime).getTime();
		return lTime;
	}

	/**
	 * 
	* @Title: getLastTime
	* @Author victor 2016年2月17日
	* @param startTime 开始时间
	* @param afterTime 提前或者延后的月份数(正数后延，负数提前)
	* @return    
	* @return Date    
	* @throws
	 */
	public static Date getLastTime(Date startTime, int afterTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MONTH, afterTime);
		return cal.getTime();
	}

	// 获取前改变n分钟后的时间，+为增加分钟，-为减小分钟
	public static Date getChangeTime(Date startTime, int afterTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.MINUTE, afterTime);
		return cal.getTime();
	}

	// 获取前改变n秒钟后的时间，+为增加秒，-为减小秒数
	public static Date getSecondTime(Date startTime, int afterTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		cal.add(Calendar.SECOND, afterTime);
		return cal.getTime();
	}
	
	/**
	 * 判断两个时间的差
	 */
	public static String  DateGap(Date startDate, Date endDate){
		 long l=endDate.getTime()-startDate.getTime();
		 long day=l/(24*60*60*1000);
		 long hour=(l/(60*60*1000)-day*24);
		 long min=((l/(60*1000))-day*24*60-hour*60);
		 long s=(l/1000-day*24*60*60-hour*60*60-min*60);
		 String  time=day+"天"+hour+"小时"+min+"分"+s+"秒";
		return time;
		
	}
	public static long  timeGap(Date startDate, Date endDate){
		 long l=endDate.getTime()-startDate.getTime();
		 long day=l/(24*60*60*1000);
		return day;
		
	}
	public static Date  timestamp2DateTime( Long  timestamp ){
		Date  date=null; 
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String d = format.format(timestamp);  
		try {
			date = format.parse(d);
			System.out.println(date.toString());
		} catch (ParseException e) {
		}
		return date;  
	}
	
	
}
