package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——全部订单实体")
public class OrderSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
 }
