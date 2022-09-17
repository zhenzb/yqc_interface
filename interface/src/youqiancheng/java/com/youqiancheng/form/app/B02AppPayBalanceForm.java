package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——余额支付实体")
public class B02AppPayBalanceForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户Id不能为空")
    private long userId;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    @NotBlank(message = "支付密码不能为空")
    private String payPwd;
    @ApiModelProperty(value = "支付订单ID",name ="payOrderId")
    @Min(value = 1,message = "订单Id不能为空")
    private long payOrderId;
    @ApiModelProperty(value = "支付金额",name ="money")
    private BigDecimal money;



}
