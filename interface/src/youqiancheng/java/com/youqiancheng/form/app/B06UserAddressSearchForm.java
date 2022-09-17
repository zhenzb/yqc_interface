package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-14
 */
@Data
@ApiModel(value = "APP——用户地址表查询实体")
public class B06UserAddressSearchForm {
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
}
