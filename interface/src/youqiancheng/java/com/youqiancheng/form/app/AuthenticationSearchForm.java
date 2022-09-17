package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "APP——实名认证查询实体")
public class AuthenticationSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
}
