package com.youqiancheng.form.client.my;

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
@ApiModel(value = "PC——余额支付实体")
public class PayBalanceForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    @Min(value = 1,message = "用户Id不能为空")
    private long userId;
    @ApiModelProperty(value = "支付密码",name ="payPwd")
    @NotBlank(message = "支付密码不能为空")
    private String payPwd;
    @ApiModelProperty(value = "订单ID",name ="orderId")
    @Min(value = 1,message = "订单Id不能为空")
    private long orderId;
    @ApiModelProperty(value = "支付金额",name ="money")
    private BigDecimal money;



}
