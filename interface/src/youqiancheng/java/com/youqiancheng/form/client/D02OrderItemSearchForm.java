package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——订单明细表查询实体")
public class D02OrderItemSearchForm {

    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户ID不能空")
    private long userId;
    @ApiModelProperty(value = "页签：1:待支付;2:已支付/待发货;3:已发货/待收货;4:已收货/已完成;5:售后中",name ="orderStatus")
    @Range(min = 1,max = 5,message = "请在按照备注状态传入状态")
    private int orderStatus;
    @ApiModelProperty(value = "是否评价：1未评价；2已评价",name ="isEvaluate")
    private int isEvaluate;
 }
