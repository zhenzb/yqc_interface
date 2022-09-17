package com.youqiancheng.controller.wechatLogin;

/**
 * 微信登录所需的配置
 */

public  class WexinLoginConfig
{

    //开放平台 网站应用appid（与支付应用appId不一致）
    public static String wx_appid = "wx30543e743ceb39b8";//"wx42bf82beaa26cc4b";
    //开放平台 appsecret
    public static String wx_appsecret ="7a3918b29f49caa7551c9e720decaf94" ;//"92c1bbfa48663660be692db6187ff706";
	//授权回调地址——域名必须和管理平台设置的回调域一致
	public static String  wx_login_notify = "http://www.youqiancheng.vip/wechatLogin/PC/wxcallback";

    public static String getWx_login_notify() {
        return wx_login_notify;
    }

    public static void setWx_login_notify(String wx_login_notify) {
        WexinLoginConfig.wx_login_notify = wx_login_notify;
    }

	public static String getWx_appid() {
		return wx_appid;
	}
	public static void setWx_appid(String wx_appid) {
		WexinLoginConfig.wx_appid = wx_appid;
	}
	public static String getWx_appsecret() {
		return wx_appsecret;
	}
	public static void setWx_appsecret(String wx_appsecret) {
		WexinLoginConfig.wx_appsecret = wx_appsecret;
	}



}
