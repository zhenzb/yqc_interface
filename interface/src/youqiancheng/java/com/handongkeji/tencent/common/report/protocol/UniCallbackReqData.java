package com.handongkeji.tencent.common.report.protocol;


import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rizenguo
 * Date: 2014/11/12
 * Time: 17:05
 */
@Data
public class UniCallbackReqData {

    private String appid;
	
    private String bank_type;
	
    private String attach;
	
    private Integer cash_fee;
	
    private String device_info;
	
    private String fee_type;
	
    private String is_subscribe;
	
    private String mch_id;
	
    private String nonce_str;
	
    private String openid;
	
    private String out_trade_no;
	

    //以下是API接口返回的对应数据
    private String result_code;
	
    private String return_code;
	
    private String sign;
	
    private String return_msg;
	
    private String time_end;
	
    private Integer total_fee;
	
    private String trade_type;
	
    private String transaction_id;
	
    //----------------------------
    private String err_code;
	
    private String err_code_des;
	
    private String sdk_version;




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
