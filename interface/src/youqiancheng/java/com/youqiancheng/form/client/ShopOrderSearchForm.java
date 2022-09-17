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
@ApiModel(value = "PC——商家订单表查询实体")
public class ShopOrderSearchForm {

    @ApiModelProperty(value = "商家id",name ="shopId")
    @Min(value = 1,message = "商家ID不能空")
    private long shopId;
    @ApiModelProperty(value = "页签：1:待支付;2:已支付/待发货;3:已发货/待收货;4:已收货/已完成;5:售后中",name ="orderStatus")
    @Range(min = 1,max = 54,message = "请在按照备注状态传入状态")
    private int orderStatus;
    @ApiModelProperty(value = "是否评价：1未评价；2已评价",name ="isEvaluate")
    private int isEvaluate;
 }
