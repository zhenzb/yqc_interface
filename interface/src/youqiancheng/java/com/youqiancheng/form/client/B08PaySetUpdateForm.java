package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "PC——支付设置修改实体")
public class B08PaySetUpdateForm {
    @ApiModelProperty(value = "主键")
    @Min(value = 1,message = "主键不能为空")
    private long id;
//    @NotBlank(message ="旧密码不能为空")
//    @ApiModelProperty(value = "旧支付密码",name ="oldPayPwd")
//    private String oldPayPwd;
    @NotBlank
    @ApiModelProperty(value = "新支付密码",name ="newPayPwd")
    @NotBlank(message ="新密码不能为空")
    private String newPayPwd;
}
