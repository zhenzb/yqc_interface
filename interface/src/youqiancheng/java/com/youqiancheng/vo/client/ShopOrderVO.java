package com.youqiancheng.vo.client;

import com.baomidou.mybatisplus.annotations.TableField;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import com.youqiancheng.mybatis.model.D06PayOrderDO;
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
@ApiModel(value = "根据商家的id查到的商家订单")
public class ShopOrderVO {
    @ApiModelProperty(value = "发货状态",name ="deliveryStatus")
    private int deliveryStatus;

    @ApiModelProperty(value = "订单编号",name ="orderNo")
    private String orderNo;

    @ApiModelProperty(value = "订单支付时间",name ="payTime")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "订单的商品图片",name ="icon")
    private String icon;

    @ApiModelProperty(value = "商品名称",name ="name")
    private String name;

    @ApiModelProperty(value = "商品规格",name ="specifications")
    private String specifications;

    @ApiModelProperty(value = "订单价格",name ="price")
    private BigDecimal price;

    @ApiModelProperty(value = "订单数量",name ="num")
    private int num;
    @TableField(exist = false )
    @ApiModelProperty(value = "订单总额",name ="total")
    private BigDecimal total;


}
