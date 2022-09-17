package com.youqiancheng.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 通用工具类
 */
public class CommonHelper {
    /**
     *时间（秒）间隔转化为文字显示间隔
     */
    public static String translateSecondsOfDisplay(String secsondsString) {
        //如果参数为空这直接返回-
        if (StringUtils.isEmpty(secsondsString) || secsondsString.equals("-")) {
            return "-";
        }
        //将字符传转换为long
        long secsonds = Long.valueOf(secsondsString).longValue();

        //秒数   整除   一天的秒数   得到的结果为  天数
        long day = secsonds / (24 * 60 * 60);
        //秒数  整除  小时秒数  得到结果为   小时数  ，然后减去   上边天数乘以每天小时数  则为去除天数后 剩余小时数
        // 另外个人觉得另一种方式(secsonds % (24 * 60 * 60))/(60 * 60)
        long hour = (secsonds / (60 * 60) - day * 24);
        //秒数/60= 总分钟数 减去 上面的天*分钟，小时*分钟，则为剩余分钟数
        // 另外个人觉得另一种方式(secsonds % (24 * 60 * 60))%(60 * 60)/60
        long min = ((secsonds / (60)) - day * 24 * 60 - hour * 60);
        //剩余秒数
        // 另外个人觉得另一种方式(secsonds % (24 * 60 * 60))%(60 * 60)%60
        long secs = (secsonds - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        String s = "";
        //根据计算结果定义返回结果格式
        if (day > 0) {
            s = day + "天" + hour + "小时" + min + "分钟" + secs + "秒";
        } else if (hour > 0) {
            s = hour + "小时" + min + "分钟" + secs + "秒";
        } else if (min > 0) {
            s = min + "分钟" + secs + "秒";
        } else {
            s = secs + "秒";
        }
        return s;
    }
    /**
     *使用";"合并字段串列表
     */
    public static String stringJoin(List<String> list)
    {
        String[] arr = new String[1];
        arr = list.toArray(arr);

       return String.join(";", arr);
    }
    /**
     *使用";"拆分字段串
     */
    public static List<String> stringSplit(String string)
    {
        if (StringUtils.isEmpty(string)) {
            return Collections.emptyList();
        }
        return Arrays.asList(string.split(";"));
    }
    /**
     *日期格式化
     */
    public static String localDateTimeFormat(LocalDateTime dateTime,String pattern){

        if(dateTime == null){
            return  "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);

        return df.format(dateTime);
    }
    /**
     *java.util.Date 转换为 LocalDateTime 类型
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }
    /**
     *对象拷贝
     */
    public static<T>  T deepCopy( T src){
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            T dest = (T) in.readObject();
            return dest;
        }
        catch (IOException ex){
            throw  new RuntimeException(ex.getMessage(),ex);
        }
        catch (ClassNotFoundException ex){
            throw  new RuntimeException(ex.getMessage(),ex);
        }
    }
	 /**
     *列表对象拷贝
     */
	public static <T> List<T> deepCopyList(List<T> src){
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<T> dest = (List<T>) in.readObject();
            return dest;
        }
        catch (IOException ex){
            throw  new RuntimeException(ex.getMessage(),ex);
        }
        catch (ClassNotFoundException ex){
            throw  new RuntimeException(ex.getMessage(),ex);
        }
    }

    /**
     * 计算两个时间点的天数差
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即要计算的天数差
     */
    public static int dateDiff(LocalDateTime dt1,LocalDateTime dt2){
        //获取第一个时间点的时间戳对应的秒数
        long t1 = dt1.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第一个时间点在是1970年1月1日后的第几天
        long day1 = t1 /(60*60*24);
        //获取第二个时间点的时间戳对应的秒数
        long t2 = dt2.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第二个时间点在是1970年1月1日后的第几天
        long day2 = t2/(60*60*24);
        //返回两个时间点的天数差
        return (int)(day2 - day1);
    }

/*    public static void main(String[] args) {
        LocalDateTime of1 = LocalDateTime.of(2018, 9, 25, 1, 1);//2018-9-25 01:01
        LocalDateTime of2 = LocalDateTime.of(2019, 9, 25, 23, 16); //2019-9-25 23:16
        LocalDateTime of4 = LocalDateTime.of(2019, 12, 2, 23, 16); //2019-9-25 23:16
        LocalDateTime of3 = LocalDateTime.now();
        LocalDateTime of5 = dateToLocalDateTime(new Date());
        System.out.println(dateDiff(of2,of5));//365
    }*/

}
