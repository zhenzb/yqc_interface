package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "视图——商家订单明细")
public class AllShopOrderItemVO {
    @ApiModelProperty(value = "订单明细id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "订单ID",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "商品id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商品封面图",name ="icon")
    private String icon;
    @ApiModelProperty(value = "商品名称",name ="name")
    private String name;
    @ApiModelProperty(value = "单价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "购买数量",name ="num")
    private int num;
    @ApiModelProperty(value = "总价",name ="totalPrice")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "购买规格参数",name ="inventory")
    private String inventory;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private int shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "订单状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "订单编码",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "评价状态",name ="isEvaluate")
    private int isEvaluate;


}
