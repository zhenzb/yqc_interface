package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——根据商家id和订单支付状态查询各个状态的订单")
public class D01OrderStatusrForm {
    @ApiModelProperty(value = "商家",name ="shopId")
    @Min(value = 1,message = "商家id不能为空")
    private long shopId;
    @ApiModelProperty(value = "订单支付状态:1,代付款,2:待发货 3:已发货 4:已完成 5-8:是退换货",name ="orderStatus")
    private int orderStatus;
}
