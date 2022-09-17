package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "订单视图")
public class OrderVO  {
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "支付订单",name ="payOrder")
    private ALLPayOrderVO payOrder;
//    @ApiModelProperty(value = "商家订单",name ="shopOrders")
//    private List<ALLOrderVO> shopOrders;
    @ApiModelProperty(value = "商家订单",name ="shopOrder")
    private  ALLOrderVO shopOrder;
//    @ApiModelProperty(value = "订单明细",name ="orderItem")
//    private List<AllShopOrderItemVO> orderItem;


}
