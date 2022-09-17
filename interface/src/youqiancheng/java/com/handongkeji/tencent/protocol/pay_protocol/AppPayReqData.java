package com.handongkeji.tencent.protocol.pay_protocol;

/**
 * User: rizenguo
 * Date: 2014/10/22
 * Time: 21:29
 */

import com.handongkeji.tencent.common.Configure;
import com.handongkeji.tencent.common.RandomStringGenerator;
import com.handongkeji.tencent.common.Signature;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求被扫支付API需要提交的数据
 */
public class AppPayReqData {

    //每个字段具体的意思请查看API文档
    private String appid = "";   //配置
    private String mch_id = "";  //配置
    private String nonce_str = "";
    private String body = "支付成功";
    private String out_trade_no = "";
    private int total_fee = 0;
    private String spbill_create_ip = "";
    private String notify_url = "";
    private String sign = "";
    private String trade_type = "APP";
    
    private String attach;

    public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
     * @param body 要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
     * @param outTradeNo 商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
     * @param totalFee 订单总金额，单位为“分”，只能整数
     * @param spBillCreateIP 订单生成的机器IP
     */
    public AppPayReqData(String body,String outTradeNo,int totalFee,String spBillCreateIP ,String attach){

        //setSdk_version(Configure.getSdkVersion());

        //微信分配的公众号ID（开通公众号之后可以获取到）
        setAppid(Configure.getThisappid());

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        setMch_id(Configure.getAppMchID());
        
        //回调地址
        setNotify_url(Configure.getNotifyUrlApp());

        
        //随机字符串，不长于32 位
        setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
      
        
        
        //要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
        setBody(body);
        
        //商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
        setOut_trade_no(outTradeNo);

        //订单总金额，单位为“分”，只能整数
        setTotal_fee(totalFee);

        //订单生成的机器IP
        setSpbill_create_ip(spBillCreateIP);

        
        setAttach(attach);
        
       //把签名数据设置到Sign这个属性中
        setSign(Signature.getSign(toMap()));

    }

    public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

   

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
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
		return "AppPayReqData [appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", body=" + body
				+ ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip="
				+ spbill_create_ip + ", notify_url=" + notify_url + ", sign=" + sign + ", trade_type=" + trade_type
				+ ", attach=" + attach + "]";
	}
    

}
