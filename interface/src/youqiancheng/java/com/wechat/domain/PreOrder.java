package com.wechat.domain;

import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName PreOrder
 * @Description TODO
 * @Author zzb
 * @Date 2022/12/31 9:12
 * @Version 1.0
 */
@Data
public class PreOrder {

    private String appid;
    private String mch_id;
    private String sub_appid;
    private String sub_mch_id;
    private String nonce_str;
    private String body;
    private String out_trade_no;
    private BigDecimal total_fee;
    private String spbill_create_ip;
    private String notify_url;
    private String trade_type;
    private String sub_openid;
    private String sign;
}
