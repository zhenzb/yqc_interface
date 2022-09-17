package com.youqiancheng.form.manager.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderQueryForm {
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "用户手机号",name ="userPhone")
    private String userPhone;
//    @ApiModelProperty(value = "发货状态",name ="deliveryStatus")
//    private Integer deliveryStatus;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private Integer orderStatus;


}
