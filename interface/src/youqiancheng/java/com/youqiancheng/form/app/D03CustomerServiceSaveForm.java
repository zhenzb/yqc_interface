package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "APP——售后表保存实体")
public class D03CustomerServiceSaveForm implements Serializable {
    @ApiModelProperty(value = "用户ID",name ="userId")
    @Min(value = 1,message = "用户ID不能为0")
    private long userId;
    @ApiModelProperty(value = "订单id",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "订单明细id",name ="orderItemId")
    private long orderItemId;
    @ApiModelProperty(value = "订单编码",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "退款金额",name ="money")
    private BigDecimal money;
    @ApiModelProperty(value = "原因",name ="reason")
    private String reason;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @ApiModelProperty(value = "退款说明",name ="explain")
    private String explain;



}
