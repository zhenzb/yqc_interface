package com.handongkeji.tencent.protocol.transfers_protocol;

import com.handongkeji.tencent.common.Configure;
import com.handongkeji.tencent.common.RandomStringGenerator;
import com.handongkeji.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TransfersReqData {

    //每个字段具体的意思请查看API文档
    private String mch_appid = "";   //微信分配的公众账号ID
    private String mchid = "";  //微信支付分配的商户号
    private String nonce_str = "";//随机字符串
    private String partner_trade_no = "";//商户订单号
    private String openid = "";//商户appid下，某用户的openid
    private String check_name = "";
    private int amount = 0;//企业付款金额，单位为分
    private String spbill_create_ip = "";//调用接口的机器Ip地址
    private String desc = "";//企业付款操作说明信息。必填。
    private String sign = "";//签名
    
    
    public TransfersReqData(String tradeno,int amount,String spBillCreateIP ,String openid){
    	
    	//微信分配的公众账号ID
    	setMch_appid(Configure.getAppid());
    	
    	//微信支付分配的商户号
    	setMchid(Configure.getMchid());
    	
    	//随机字符串
    	setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
    	
    	//商户订单号
    	setPartner_trade_no(tradeno);
    	
    	//商户appid下，某用户的openid
    	setOpenid(openid);
    	
    	//校验收款人姓名
    	setCheck_name("NO_CHECK");//NO_CHECK：不校验真实姓名 
    							  //FORCE_CHECK：强校验真实姓名
    	
    	//企业付款操作说明信息。必填。
    	setDesc("提现");
    	
    	//转账金额
    	setAmount(amount);
    	
    	//调用接口的机器Ip地址
    	setSpbill_create_ip(Configure.getIP());
    	
    	//把签名数据设置到Sign这个属性中
    	setSign(Signature.getSign(toMap()));
    }
    
	public String getMch_appid() {
		return mch_appid;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
    
    
	@Override
	public String toString() {
		return "TransfersReqData [mch_appid=" + mch_appid + ", mchid=" + mchid + ", nonce_str=" + nonce_str
				+ ", partner_trade_no=" + partner_trade_no + ", openid=" + openid + ", amount="
				+ amount + ", sign=" + sign + ", desc=" + desc + ""
				+ ", spbill_create_ip=" + spbill_create_ip + "]";
	}
	
	
	
	
	  public Map<String,Object> toMap(){
	       Map<String,Object> map = new HashMap<String, Object>();
	       Field[] fields = this.getClass().getDeclaredFields();
	       for (Field field : fields) {
	           Object obj;
	           try {
	               obj = field.get(this);
	               if(obj!=null){
	                   map.put(field.getName(), obj);
	               }
	           } catch (IllegalArgumentException e) {
	               e.printStackTrace();
	           } catch (IllegalAccessException e) {
	               e.printStackTrace();
	           }
	       }
	       return map;
	   }
    
}
