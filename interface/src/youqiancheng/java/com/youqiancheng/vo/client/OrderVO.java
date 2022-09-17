package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "订单视图")
public class OrderVO {
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "支付订单",name ="payOrder")
    private D06PayOrderDO payOrder;
    @ApiModelProperty(value = "商家订单",name ="shopOrders")
    private List<D01OrderVO> shopOrders;
    @ApiModelProperty(value = "订单明细",name ="orderItem")
    private List<D02OrderItemDO> orderItem;


}
