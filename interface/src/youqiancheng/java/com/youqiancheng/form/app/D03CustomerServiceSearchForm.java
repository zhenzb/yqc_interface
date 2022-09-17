package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "APP——售后表查询实体")
public class D03CustomerServiceSearchForm {
    @ApiModelProperty(value = "用户ID",name ="userId")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1,message = "用户ID不能为0")
    private Long userId;
}
