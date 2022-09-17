package com.handongkeji.util;

import java.util.*;

/**
 *  对url进行解密
 * @ClassName UrlKeyUtil
 * @PackageName com.handongkeji.util
 * @CreateOn 2016年4月11日下午6:10:55
 * @Site http://www.handongkeji.com
 * @author hwd
 * @Copyrights 2016年4月11日 handongkeji All rights reserved.
 */
public class UrlKeyUtil {
	
	public static boolean urlDecode(Map<String, String> map, String urlKey){
		Set<String> set = map.keySet();
		String url = "";
		for(String str : set){
			String value = map.get(str);
			if("".equals(url)){
				url = str + "=" + value;
			}else{
				url = url + "&" + str + "=" + value; 
			}
		}
		url = MD5Util.encrypt(url);
		if(urlKey.equals(url)){
			return true;
		}
		return false;
	}
	
	public static String urlSort(Map<String, String> map){
		Set<String> set = map.keySet();
		List<String> list = new ArrayList<String>();
		for(String str : set){
			list.add(str);
		}
		String url = "";
		Collections.sort(list);
		for(String str : list){
			String value = map.get(str);
			if("".equals(url)){
				url = value + "=" + str;
			}else{
				url = url + "&" + value + "=" + str; 
			}
		}
		return url;
	}
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"snc", "pa", "am", "sk", "jugg", "spe",  "sf"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
	   public static void main(String[] args) {
	       
	    }

}
