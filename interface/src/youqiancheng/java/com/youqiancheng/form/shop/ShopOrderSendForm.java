package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单状态变更表单
 */
@Data
public class ShopOrderSendForm {
    @ApiModelProperty("订单ID")
    private long orderId;
    @ApiModelProperty("flag")
    private int flag;
    @ApiModelProperty("快递公司")
    private String expressCode;
    @ApiModelProperty("快递公司名称")
    private String expressName;
    @ApiModelProperty("快递单号")
    private String expressNumber;
}
