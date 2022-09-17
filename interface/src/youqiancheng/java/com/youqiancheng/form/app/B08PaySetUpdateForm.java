package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——支付设置修改实体")
public class B08PaySetUpdateForm   {
    @ApiModelProperty(value = "主键")
    private long id;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
}
