package com.handongkeji.tencent.common.report.protocol;

/**
 * User: rizenguo
 * Date: 2014/11/12
 * Time: 17:06
 */
public class UniCallbackResData {

    //以下是API接口返回的对应数据
    private String return_code;
    private String return_msg;
    //以下是API接口返回的对应数据
    private String result_code;
    private String appid ;
    private String mch_id ;
    private String nonce_str;
    private String sign;
    private String err_code_des;
    private String trade_type;
    private String prepay_id;
    private String attach;
	private String code_url;

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

	@Override
	public String toString() {
		return "UniCallbackResData{" +
				"return_code='" + return_code + '\'' +
				", return_msg='" + return_msg + '\'' +
				", result_code='" + result_code + '\'' +
				", appid='" + appid + '\'' +
				", mch_id='" + mch_id + '\'' +
				", nonce_str='" + nonce_str + '\'' +
				", sign='" + sign + '\'' +
				", err_code_des='" + err_code_des + '\'' +
				", trade_type='" + trade_type + '\'' +
				", prepay_id='" + prepay_id + '\'' +
				", attach='" + attach + '\'' +
				", code_url='" + code_url + '\'' +
				'}';
	}
}
