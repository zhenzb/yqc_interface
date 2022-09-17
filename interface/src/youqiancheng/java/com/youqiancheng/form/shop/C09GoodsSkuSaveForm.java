package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商品规格保存实体")
public class C09GoodsSkuSaveForm  {
    @ApiModelProperty(value = "已选择的商品属性",name ="specifications")
    private String specifications;
    @ApiModelProperty(value = "对应规格商品数量",name ="num")
    private int num;
    @ApiModelProperty(value = "对应规格金额",name ="goodsPrice")
    private BigDecimal goodsPrice;
    @ApiModelProperty(value = "商品Id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商品描述",name = "goodsDesc")
    private String goodsDesc;

}
