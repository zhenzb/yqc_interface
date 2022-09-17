package com.youqiancheng.controller.wechatpay.weixinpay;

public class UnifiedOrderApp {
	private String appid;// 应用ID
	private String partnerid;// 商户号
	private String prepayid;// 预支付交易会话ID
	private String packagestr;// 扩展字段
	private String noncestr;// 随机字符串
	private String timestamp;// 时间戳
	private String sign;// 签名

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPackagestr() {
		return packagestr;
	}

	public void setPackagestr(String packagestr) {
		this.packagestr = packagestr;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
