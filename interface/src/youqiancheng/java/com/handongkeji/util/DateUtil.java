package com.handongkeji.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	 //判断选择的日期是否是本周  
    public static boolean isThisWeek(long time)  
    {  
        Calendar calendar = Calendar.getInstance();  
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);  
        calendar.setTime(new Date(time));  
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);  
        if(paramWeek==currentWeek){  
           return true;  
        }  
        return false;  
    }  
    //判断选择的日期是否是今天  
    public static boolean isToday(long time)  
    {  
       return isThisTime(time,"yyyy-MM-dd");  
    }  
    //判断选择的日期是否是本月  
    public static boolean isThisMonth(long time)  
    {  
         return isThisTime(time,"yyyy-MM");  
    }  
    private static boolean isThisTime(long time,String pattern) {  
        Date date = new Date(time);  
         SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
         String param = sdf.format(date);//参数时间  
         String now = sdf.format(new Date());//当前时间  
         if(param.equals(now)){  
           return true;  
         }  
         return false;  
    }

    public static String getNowDateTime(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    public static String getStringNowDateTime(){
        Date date = new Date();
        String strDateFormat = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }
    public static String getCurrentDateTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }
}
