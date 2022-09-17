package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "PC——实名认证查询实体")
public class AuthenticationSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
}
