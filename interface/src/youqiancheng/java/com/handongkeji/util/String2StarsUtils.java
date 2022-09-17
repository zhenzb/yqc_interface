package com.handongkeji.util;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串关键信息替换为星号
 * @author victor
 * 2014-10-28
 *
 */
public class String2StarsUtils {

	/**
	 * 用户住址信息
	 * @param address
	 * @return
	 */
	public static String addressToStar(String address){
		if(StringUtils.isEmpty(address)){
			return address;
		}
		
		int length = address.length();
		if(length > 6){
			return address.substring(0, 6) + "*****";
		}
		
		return address;
		
	}
	
}
