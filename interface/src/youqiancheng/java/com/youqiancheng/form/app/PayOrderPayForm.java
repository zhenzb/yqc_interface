package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-05-12
 */
@Data
@ApiModel(value = "支付订单支付实体")
public class PayOrderPayForm {
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "支付方式：1，支付宝，2微信，3余额",name ="payType")
    private Integer payType;
    @ApiModelProperty(value = "第三方交易流水号",name ="tradeNo")
    private String tradeNo;
}
