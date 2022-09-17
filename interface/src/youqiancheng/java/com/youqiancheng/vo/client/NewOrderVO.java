package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "订单视图")
public class NewOrderVO {
    @ApiModelProperty(value = "订单ID",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "订单明细",name ="orderItem")
    private List<D02OrderItemDO> orderItem;

    @ApiModelProperty(value = "快递公司名称",name ="expressName")
    private String expressName;
    @ApiModelProperty(value = "快递公司简码",name ="expressCode")
    private String expressCode;
    @ApiModelProperty(value = "快递单号",name ="expressNumber")
    private String expressNumber;
    @ApiModelProperty(value = "是否面对面",name = "flag")
    private int flag;
    @ApiModelProperty(value = "是否实体店商品",name = "type")
    private int type;
}
