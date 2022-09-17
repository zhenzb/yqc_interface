package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——支付设置查询实体")
public class B08PaySetSearchForm  {
    @ApiModelProperty(value = "主键id")
    private long id;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
}
