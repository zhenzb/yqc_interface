package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——购物车保存实体")
public class B04ShoppingCartSaveForm {
    private static final long serialVersionUID = 1L;
    @Min(message = "用户id最小为1",value = 1)
    @ApiModelProperty(value = "用户ID",name ="userId")
    private long userId;
    @ApiModelProperty(value = "一级分类",name ="categoryId")
    private long categoryId;
    @Min(message = "商家id最小为1",value = 1)
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @Min(message = "商品id最小为1",value = 1)
    @ApiModelProperty(value = "商品表id",name ="goodsId")
    private long goodsId;
    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
    @ApiModelProperty(value = "商品logog",name ="goodsIcon")
    private String goodsIcon;
    @Min(message = "规格ID最小为1",value = 1)
    @ApiModelProperty(value = "规格ID——上一步获取sku库存接口返回的记录ID",name ="skuId")
    private long skuId;
    @ApiModelProperty(value = "规格参数",name ="inventory")
    @NotBlank(message = "规格参数不能为空")
    private String inventory;
    @ApiModelProperty(value = "加入购物车数量",name ="commodityNumber")
    @Min(message = "加入购物车数量最小为1",value = 1)
    private int commodityNumber;
    @ApiModelProperty(value = "单价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "总价",name ="totalPrice")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;

}
