package com.youqiancheng.controller.wechatpay.weixinpay.util;

import javax.servlet.http.HttpServletRequest;

public class IpAddressUtil
{
    public static String getIpAddr(HttpServletRequest request)
    {
        if (request.getHeader("x-forwarded-for") == null)
        {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static String formatIpAddr(HttpServletRequest request)
    {
        String ips = getIpAddr(request);
        if (ips.indexOf(",") != -1)
        {
            return ips.split(",")[0];
        }
        else
        {
            return ips;
        }
    }
}
