package com.youqiancheng.form.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-08
 */
@Data
@ApiModel(value = "消息查询实体")
public class A15MessageSearchForm {
    @ApiModelProperty(value = "类型",name ="type")
    private int type;
}
