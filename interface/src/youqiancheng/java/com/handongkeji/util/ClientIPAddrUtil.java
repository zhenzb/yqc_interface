package com.handongkeji.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客服端IP地址
 * @author victor
 * 2015-7-06
 *
 */
public class ClientIPAddrUtil {
	
	public static String getClientIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
	      ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
	      ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
	      ip = request.getRemoteAddr();
	    }
	    return ip;
	}

}
