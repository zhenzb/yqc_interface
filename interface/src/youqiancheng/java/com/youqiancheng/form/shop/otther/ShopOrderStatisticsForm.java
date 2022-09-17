package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单统计筛选表单
 */
@Data
public class ShopOrderStatisticsForm {
    @ApiModelProperty("支付方式")
    private int payType;
    @ApiModelProperty("订单支付状态")
    private int orderStatus;
    @ApiModelProperty("订单支付状态")
    private int deliverystatus;
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
}
