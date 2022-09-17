package com.handongkeji.tencent.protocol.pay_protocol;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */

import com.handongkeji.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API需要提交的数据
 */
public class JsWebPaySignReqData {

    //每个字段具体的意思请查看API文档
    private String appId  = "";   //配置
    private String timeStamp  = "";  //配置
    private String nonceStr  = "";
    private String package1  = "";
    private String signType  = "";
    private String paySign  = "";
   
    


	/**
     * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     * @param totalFee 订单总金额，单位为“分”，只能整数
     * @param spBillCreateIP 订单生成的机器IP
     */
    public JsWebPaySignReqData(String body,String outTradeNo,int totalFee,String spBillCreateIP ,String attach,String openid){

       
        
       //把签名数据设置到Sign这个属性中
    	setSignType(Signature.getSign(toMap()));

    }

    
	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getTimeStamp() {
		return timeStamp;
	}



	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}



	public String getNonceStr() {
		return nonceStr;
	}



	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}



	public String getPackage1() {
		return package1;
	}



	public void setPackage1(String package1) {
		this.package1 = package1;
	}



	public String getSignType() {
		return signType;
	}



	public void setSignType(String signType) {
		this.signType = signType;
	}



	public String getPaySign() {
		return paySign;
	}



	public void setPaySign(String paySign) {
		this.paySign = paySign;
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

	@Override
	public String toString() {
		return super.toString();
	}

}
