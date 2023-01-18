package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "订单明细表实体")
@TableName("d02_order_item")
public class D02OrderItemDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "订单明细id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "订单ID",name ="orderId")
    private long orderId;
    @ApiModelProperty(value = "支付订单ID",name ="payOrderId")
    private long payOrderId;
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
    @ApiModelProperty(value = "评价状态",name ="isEvaluate")
    private int isEvaluate;
    @ApiModelProperty(value = "规格ID",name ="skuId")
    private long skuId;
    @ApiModelProperty(value = "购买规格参数",name ="inventory")
    private String inventory;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "订单状态",name ="orderStatus")
    private int orderStatus;
    @TableField(exist = false)
    @ApiModelProperty(value = "订单编码",name ="orderNo")
    private String orderNo;
    @TableField(exist = false)
    @ApiModelProperty(value = "商品类型",name ="type")
    private String type;
    @TableField(exist = false)
    @ApiModelProperty(value = "平台帮你付",name ="setOffFund")
    private BigDecimal setOffFund;

}
