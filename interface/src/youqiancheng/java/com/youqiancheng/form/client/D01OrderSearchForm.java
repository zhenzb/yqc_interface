package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——商城业务订单表查询实体")
public class D01OrderSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "发货状态",name ="deliveryStatus")
    private int deliveryStatus;
    @ApiModelProperty(value = "评价状态",name ="isEvaluate")
    private int isEvaluate;

}
