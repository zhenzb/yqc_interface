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
@ApiModel(value = "PC——支付设置保存实体")
public class B08PaySetSaveForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户Id不能为空")
    private long userId;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;




}
