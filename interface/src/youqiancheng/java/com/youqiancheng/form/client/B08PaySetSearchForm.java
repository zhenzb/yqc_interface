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
@ApiModel(value = "PC——支付设置查询实体")
public class B08PaySetSearchForm  {
    @ApiModelProperty(value = "主键ID",name ="id")
    @Min(value = 1,message = "主键ID不能为空")
    private Long id;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
}
