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
import java.util.List;

/**
 * Author tyf
 * Date  2020-05-12
 */
@Data
@ApiModel(value = "支付订单实体")
@TableName("d06_pay_order")
public class D06PayOrderDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "用户id",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "用户昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "用户手机号",name ="userPhone")
    private String userPhone;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "支付方式",name ="payType")
    private Integer payType;
    @ApiModelProperty(value = "第三方交易流水号",name ="tradeNo")
    private String tradeNo;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private Integer orderStatus;
    @ApiModelProperty(value = "买家留言",name ="buyMsg")
    private String buyMsg;
    @ApiModelProperty(value = "收货人姓名",name ="shippingName")
    private String shippingName;
    @ApiModelProperty(value = "收货人电话",name ="shippingPhone")
    private String shippingPhone;
    @ApiModelProperty(value = "省",name ="province")
    private String province;
    @ApiModelProperty(value = "市",name ="city")
    private String city;
    @ApiModelProperty(value = "区",name ="area")
    private String area;
    @ApiModelProperty(value = "是否开发票(1,不开票，2开发票)",name ="invoiceFlag")
    private Integer invoiceFlag;
    @ApiModelProperty(value = "发票类型",name ="invoiceType")
    private String invoiceType;
    @ApiModelProperty(value = "发票内容",name ="invoiceContent")
    private String invoiceContent;
    @ApiModelProperty(value = "发票抬头",name ="invoiceTitle")
    private String invoiceTitle;
    @ApiModelProperty(value = "收货人地址",name ="shippingAddress")
    private String shippingAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间",name ="payTime")
    private LocalDateTime payTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单完成时间",name ="endTime")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "删除标记",name ="deleteFlag")
    private int deleteFlag;
    @TableField(exist = false)
    @ApiModelProperty(value = "订单明细",name ="orderItem")
    private List<D02OrderItemDO> orderItem;
    @TableField(exist = false)
    @ApiModelProperty(value = "商家订单",name ="shopOrders")
    private List<D01OrderDO> shopOrders;
    @TableField(exist = false)
    @ApiModelProperty(value = "剩余支付时间",name ="surplusTime")
    private String surplusTime;
    @TableField(exist = false)
    @ApiModelProperty(value = "商品总数",name ="count")
    private int count;


}
