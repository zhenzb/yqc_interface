package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——支付设置保存实体")
public class B08PaySetSaveForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    private String payPwd;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;




}
