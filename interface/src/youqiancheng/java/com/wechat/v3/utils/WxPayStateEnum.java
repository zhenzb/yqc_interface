package com.wechat.v3.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信支付状态值
 * @Author smilehan
 */
@Getter
@AllArgsConstructor
public enum WxPayStateEnum {
    SUCCESS(0, "SUCCESS", "支付成功"),
    REFUND(1, "REFUND", "转入退款"),
    NOTPAY(2, "NOTPAY", "未支付"),
    CLOSED(3, "CLOSED", "已关闭"),
    REVOKED(4, "REVOKED", "已撤销（付款码支付）"),
    USERPAYING(5, "USERPAYING", "用户支付中（付款码支付）"),
    PAYERROR(6, "PAYERROR", "支付失败(其他原因，如银行返回失败)"),
    ACCEPT(7, "ACCEPT", "已接收，等待扣款"),
    ABSENCE(8, "ABSENCE", "订单不存在"),
    OK(9, "OK", "OK"),
    PROCESS(10, "PROCESSING", "PROCESSING")
    ;
    private Integer code;

    private String name;

    private String description;

    public static WxPayStateEnum getByName(String name) {
        for (WxPayStateEnum wxPayStateEnum : WxPayStateEnum.values()) {
            if (wxPayStateEnum.getName().equals(name)) {
                return wxPayStateEnum;
            }
        }
        return null;
    }

}
