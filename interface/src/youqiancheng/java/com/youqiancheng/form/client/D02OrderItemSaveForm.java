package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——订单明细表保存实体")
public class D02OrderItemSaveForm {
    @ApiModelProperty(value = "购物记录ID",name ="cartId")
    private long cartId;
    @ApiModelProperty(value = "商品id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "商品封面图",name ="icon")
    private String goodsIcon;
    @ApiModelProperty(value = "商品名称",name ="name")
    private String goodsName;
    @ApiModelProperty(value = "单价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "购买数量",name ="num")
    private int num;
    @ApiModelProperty(value = "总价",name ="totalPrice")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "规格ID",name ="skuId")
    private long skuId;
    @ApiModelProperty(value = "购买规格参数",name ="inventory")
    private String inventory;
    @ApiModelProperty(value = "街道Id",name ="streetId")
    private Long streetId;
    @ApiModelProperty(value = "邮费",name ="goodsPostage")
    private BigDecimal goodsPostage;
    @ApiModelProperty(value = "平台帮付",name ="bangnifu")
    private BigDecimal bangnifu;


}
