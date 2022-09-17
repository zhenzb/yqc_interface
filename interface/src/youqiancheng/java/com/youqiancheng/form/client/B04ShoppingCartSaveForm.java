package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——购物车保存实体")
public class B04ShoppingCartSaveForm {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
    @ApiModelProperty(value = "一级分类",name ="categoryId")
    private long categoryId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    @Min(value = 1,message = "商家ID不能为空")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "商品表id",name ="goodsId")
    @Min(value = 1,message = "商品ID不能为空")
    private long goodsId;
    @ApiModelProperty(value = "商品名称",name ="goodsName")
    private String goodsName;
    @ApiModelProperty(value = "商品logog",name ="goodsIcon")
    private String goodsIcon;
    @ApiModelProperty(value = "规格ID上一步获取sku库存接口返回的记录ID",name ="skuId")
    private long skuId;
    @ApiModelProperty(value = "规格参数",name ="inventory")
    private String inventory;
    @ApiModelProperty(value = "数量",name ="commodityNumber")
    @Min(value = 1,message = "数量不能为空")
    private int commodityNumber;
    @ApiModelProperty(value = "单价",name ="price")
    private BigDecimal price;
    @ApiModelProperty(value = "总价",name ="totalPrice")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;

}
