package com.handongkeji.tencent.common;

import com.handongkeji.util.ResourceUtil;

/**
 * User: rizenguo Date: 2014/10/29 Time: 14:40 这里放置各种配置数据
 */
public class Configure {

	// sdk的版本号
	private static final String sdkVersion = "java sdk 1.0.1";

	// 这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	// private static String appID = "wxd4e1e2c091341fa9"; //学留学
	// private static String mchID = "1269726101";
	// private static String certLocalPath =
	// "c:\\cert\\apiclient_cert_liuxue.p12"; //学留学
	// private static String certPassword = "1269726101";

	private static String key = "qihuangliuhe2019qihuangliuhe2019";

	// 微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = ResourceUtil.getConfigByName("wx.appid");// "wx07f341317df0f4cf";

	// 微信分配的app应用的appID
	private static String thisappid = ResourceUtil.getConfigByName("app.appid");// "wx07f341317df0f4cf";
																			// //lxyc
	// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = ResourceUtil.getConfigByName("wx.pay.mchid");// "1295650001";
																				// //lxyc
	// 微信支付分配的app应用的商户号ID
	private static String appMchID = ResourceUtil.getConfigByName("app.mch_id");

	// HTTPS证书的本地路径
	private static String certLocalPath = "";

	// HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = ResourceUtil.getConfigByName("wx.pay.certpassword");// "1295650001";
																								// //lxyc

	// 受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	// 是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	// 机器IP
	// private static String ip = "58.135.87.110";
	private static String ip = "182.61.47.124";

	// 以下是几个API的路径：
	// 1）被扫支付API

	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

	// 2）被扫支付查询API
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

	// 3）退款API
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	public static String getNOTIFY_URL() {
		return NOTIFY_URL;
	}

	public static String getUNIFIED_ORDER_API() {
		return UNIFIED_ORDER_API;
	}

	// 4）退款查询API
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

	// 5）撤销API
	public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	// 6）下载对账单API
	public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

	// 7) 统计上报API
	public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

	// 8) 回调URL 在properties文件中配置
	public static String NOTIFY_URL = ResourceUtil.getConfigByName("wx.notify.url");

	public static String getNotifyUrlApp() {
		return NOTIFY_URL_APP;
	}

	public static void setNotifyUrlApp(String notifyUrlApp) {
		NOTIFY_URL_APP = notifyUrlApp;
	}

	public static String NOTIFY_URL_APP = ResourceUtil.getConfigByName("app.notify.url");

	// 9) 统一下单API
	public static String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.handongkeji.tencent.common.HttpsRequest";

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public static void setMchID(String mchID) {
		Configure.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		Configure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		Configure.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		Configure.ip = ip;
	}

	public static String getKey() {
		return key;
	}

	public static String getAppid() {
		return appID;
	}

	public static String getThisappid() {
		return thisappid;
	}

	public static void setThisappid(String thisappid) {
		Configure.thisappid = thisappid;
	}

	public static String getAppMchID() {
		return appMchID;
	}

	public static void setAppMchID(String appMchID) {
		Configure.appMchID = appMchID;
	}

	public static String getMchid() {
		return mchID;
	}

	public static String getSubMchid() {
		return subMchID;
	}

	public static String getCertLocalPath() {
		return certLocalPath;
	}

	public static String getCertPassword() {
		return certPassword;
	}

	public static String getIP() {
		return ip;
	}

	public static void setHttpsRequestClassName(String name) {
		HttpsRequestClassName = name;
	}

	public static String getSdkVersion() {
		return sdkVersion;
	}

	public static void main(String[] args) {
		// 方法1：通过SecurityManager的保护方法getClassContext()
		String clazzName = new SecurityManager() {
			public String getClassName() {
				return getClassContext()[1].getName();
			}
		}.getClassName();
		System.out.println(clazzName);
		// 方法2：通过Throwable的方法getStackTrace()
		String clazzName2 = new Throwable().getStackTrace()[0].getClassName();
		System.out.println(clazzName2);
		// 方法3：通过分析匿名类名称()
		String clazzName3 = new Object() {
			public String getClassName() {
				String clazzName = this.getClass().getName();
				return clazzName.substring(0, clazzName.lastIndexOf('$'));
			}
		}.getClassName();
		System.out.println(clazzName3);
	}

}
