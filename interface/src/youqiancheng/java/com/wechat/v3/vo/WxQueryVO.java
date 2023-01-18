package com.wechat.v3.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName WxQueryVO
 * @Description 微信订单查询
 * @Author zzb
 * @Date 2022/12/31 12:01
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class WxQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单号
     */
    private String outTradeNo;

    /**
     * 交易状态（0:支付成功 1：转入退款 2：未支付 3：已关闭 4:已撤销（付款码支付）" +
     *             " 5：用户支付中（付款码支付） 6:支付失败(其他原因，如银行返回失败) 7：已接收，等待扣款 8: 订单不存在）
     */
    private Integer tradeState;

    /**
     * 交易状态描述
     */
    private String tradeStateDesc;

    /**
     * 支付单号
     */
    private String transactionId;
}
