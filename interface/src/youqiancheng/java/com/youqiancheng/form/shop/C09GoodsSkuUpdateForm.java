package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-10
 */
@Data
@ApiModel(value = "商品规格修改实体")
public class C09GoodsSkuUpdateForm implements Serializable {
    @ApiModelProperty(value = "id(数据库自增)" )
    private Long id;
    @ApiModelProperty(value = "对应规格商品数量",name ="num")
    private int num;
    @ApiModelProperty(value = "对应规格金额",name ="goodsPrice")
    private BigDecimal goodsPrice;
}
