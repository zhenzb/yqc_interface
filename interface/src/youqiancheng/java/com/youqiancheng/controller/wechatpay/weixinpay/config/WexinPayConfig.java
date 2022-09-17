package com.youqiancheng.controller.wechatpay.weixinpay.config;

/**
 * 微信支付所需的配置
 */

public class WexinPayConfig
{

    //开放平台 appid
    public static String wx_appid = "wxf3bae65a342d549c";//"wx42bf82beaa26cc4b";
    //开放平台 appsecret
    public static String wx_appsecret ="9ad34c340c5d419057f8eed58414a08c" ;//"92c1bbfa48663660be692db6187ff706";

	//商户平台apikey  32位秘钥
	public static String  wx_apikey = "asdawe2eedacwr2dsec23r23dwr3rw23";
    //开放平台 商户号
    public static String wx_mchid = "1597435631";//"1295650001";//1556447741
    //微信回调url 正式环境回调地址
	public static String  wx_notify = "http://client.youqiancheng.vip/wxpay/notifyWxpay";
	//本地
	//public static String  wx_notify = "http://zhen.vaiwan.com/wxpay/notifyWxpay";
	//测试的
	//public static String  wx_notify = "http://49.233.136.163:8070/wxpay/notifyWxpay";
	//获取支付签名url
    public static String  wx_unifiedoroer_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //转账
    public static String  wx_transfers_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    //获取退款地址
    public static String  wx_refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //查询支付订单状态url
    public static String  wx_orderquery_url = "https://api.mch.weixin.qq.com/pay/orderquery";

    public static String getWx_apikey() {
		return wx_apikey;
	}
	public static void setWx_apikey(String wx_apikey) {
		WexinPayConfig.wx_apikey = wx_apikey;
	}
	public static String getWx_appid() {
		return wx_appid;
	}
	public static void setWx_appid(String wx_appid) {
		WexinPayConfig.wx_appid = wx_appid;
	}
	public static String getWx_appsecret() {
		return wx_appsecret;
	}
	public static void setWx_appsecret(String wx_appsecret) {
		WexinPayConfig.wx_appsecret = wx_appsecret;
	}
	public static String getWx_mchid() {
		return wx_mchid;
	}
	public static void setWx_mchid(String wx_mchid) {
		WexinPayConfig.wx_mchid = wx_mchid;
	}
	public static String getWx_notify() {
		return wx_notify;
	}
	public static void setWx_notify(String wx_notify) {
		WexinPayConfig.wx_notify = wx_notify;
	}
	public static String getWx_unifiedoroer_url() {
		return wx_unifiedoroer_url;
	}
	public static void setWx_unifiedoroer_url(String wx_unifiedoroer_url) {
		WexinPayConfig.wx_unifiedoroer_url = wx_unifiedoroer_url;
	}
	public static String getWx_orderquery_url() {
		return wx_orderquery_url;
	}
	public static void setWx_orderquery_url(String wx_orderquery_url) {
		WexinPayConfig.wx_orderquery_url = wx_orderquery_url;
	}

	public static String getWx_transfers_url() {
		return wx_transfers_url;
	}

	public static void setWx_transfers_url(String wx_transfers_url) {
		WexinPayConfig.wx_transfers_url = wx_transfers_url;
	}

	public static String getWx_refund_url() {
		return wx_refund_url;
	}

	public static void setWx_refund_url(String wx_refund_url) {
		WexinPayConfig.wx_refund_url = wx_refund_url;
	}
}
