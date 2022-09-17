package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——全部订单实体")
public class OrderSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1,message = "用户ID不能小于1")
    private Long userId;
 }
