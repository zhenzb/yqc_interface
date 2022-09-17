package com.youqiancheng.form.client.my;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author nl
 * @version 1.0
 * @date 2020/4/15 16:29
 */
@Data
@ApiModel(value="订单查询",description="订单查询")
public class D01OrderQueryForm {
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
}

