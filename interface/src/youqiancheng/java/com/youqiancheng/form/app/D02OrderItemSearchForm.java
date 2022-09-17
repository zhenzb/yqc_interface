package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——订单明细表查询实体")
public class D02OrderItemSearchForm {

    @ApiModelProperty(value = "用户id",name ="userId")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1,message = "用户ID不能小于1")
    private Long userId;
    @ApiModelProperty(value = "页签：1:待支付;2:已支付/待发货;3:已发货/待收货;4:已收货/已完成;5:售后中",name ="orderStatus")
    @NotNull(message = "状态不能为空")
    @Range(min = 1,max = 5,message = "状态（不同页签）取值：1:待支付;2:已支付/待发货;3:已发货/待收货;4:已收货/已完成;5:售后中")
    private Integer orderStatus;
    @ApiModelProperty(value = "是否评价：1未评价；2已评价",name ="isEvaluate")
    @Range(min = 1,max = 2,message = "是否评价取值范围：1未评价，2已评价")
    private Integer isEvaluate;
 }
