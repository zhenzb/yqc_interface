package com.wechat.v3.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName PayOrderDTO
 * @Description 支付下单
 * @Author zzb
 * @Date 2022/12/31 11:56
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class PayOrderDTO {
    private Long userId;
    private Integer totalPrice;
    private String goodsName;
    private String openId;
    private String orderSn;
}
