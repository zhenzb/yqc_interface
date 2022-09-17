package com.handongkeji.util;

import java.util.Date;

public class TimeCountUtil {
	private static long minute = 1000 * 60;
	private static long hour = minute * 60;
	private static long day = hour * 24;
	private static long month = day * 30;
	private static long year = day * 30;
	
	public static String getDateDiff(long dateTimeStamp) {

		String result = "";
		long now = new Date().getTime();
		long diffValue = now - dateTimeStamp;
		if (diffValue < 0) {
			return "";
		}
		long yearC = diffValue/ year;
		long monthC = diffValue / month;
		long weekC = diffValue / (7 * day);
		long dayC = diffValue / day;
		long hourC = diffValue / hour;
		long minC = diffValue / minute;
		if(yearC >= 1){
			result = "更早";
		}else if (monthC >= 1) {
			result = (int) monthC + "个月前";
		} else if (weekC >= 1) {
			result = (int) weekC + "周前";
		} else if (dayC >= 1) {
			result = (int) dayC + "天前";
		} else if (hourC >= 1) {
			result = (int) hourC + "个小时前";
		} else if (minC >= 1) {
			result = (int) minC + "分钟前";
		} else
			result = "刚刚发表";
		return result;
	}
}
