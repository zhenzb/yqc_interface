package com.wechat.v3.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName WxPayVO
 * @Description 微信下单返回
 * @Author zzb
 * @Date 2022/12/31 11:57
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class WxPayVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 预支付交易会话标识小程序下单接口返回的prepay_id参数值
     */
    private String prepayId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名
     */
    private String paySign;
}
