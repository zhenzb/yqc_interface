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
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "商城业务订单表实体")
@TableName("d01_order")
public class D01OrderDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
    @ApiModelProperty(value = "用户昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "用户手机号",name ="userPhone")
    private String userPhone;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
    @ApiModelProperty(value = "支付方式",name ="payType")
    private int payType;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "发货状态",name ="deliveryStatus")
    private int deliveryStatus;
    @ApiModelProperty(value = "买家留言",name ="buyMsg")
    private String buyMsg;
    @ApiModelProperty(value = "商家",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "卖家留言",name ="saleMsg")
    private String saleMsg;
    @ApiModelProperty(value = "快递公司名称",name ="expressName")
    private String expressName;
    @ApiModelProperty(value = "快递公司简码",name ="expressCode")
    private String expressCode;
    @ApiModelProperty(value = "快递单号",name ="expressNumber")
    private String expressNumber;
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
    @ApiModelProperty(value = "收货人地址",name ="shippingAddress")
    private String shippingAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间",name ="payTime")
    private LocalDateTime payTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "发货时间",name ="sendTime")
    private LocalDateTime sendTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "收货时间",name ="takeTime")
    private LocalDateTime takeTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单完成时间",name ="endTime")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "第三方交易流水号",name ="tradeNo")
    private String tradeNo;
    @ApiModelProperty(value = "是否开发票",name ="invoiceFlag")
    private int invoiceFlag;
    @ApiModelProperty(value = "发票类型",name ="invoiceType")
    private String invoiceType;
    @ApiModelProperty(value = "发票内容",name ="invoiceContent")
    private String invoiceContent;
    @ApiModelProperty(value = "发票抬头",name ="invoiceTitle")
    private String invoiceTitle;
    @ApiModelProperty(value = "支付订单ID",name ="payOrderId")
    private Long payOrderId;
    @ApiModelProperty(value = "删除标记",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "面对面标记",name ="flag")
    private int flag;


    @TableField(exist = false)
    @ApiModelProperty(value = "销售总额",name="totalSales")
    private BigDecimal totalSales;
    @TableField(exist = false)
    @ApiModelProperty(value = "支付订单编码",name="payOrderNo")
    private String payOrderNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "订单统计",name="totalOrders")
    private int totalOrders;

    @TableField(exist = false)
    @ApiModelProperty(value = "订单明细")
    private D02OrderItemDO d02OrderItem;

    @TableField(exist = false)
    @ApiModelProperty(value = "商品")
    private C01GoodsDO c01Goods;

    @ApiModelProperty(value = "订单明细表")
    @TableField(exist = false)
    private List<D02OrderItemDO> orderItem;

    @ApiModelProperty(value = "用户信息")
    @TableField(exist = false)
    private B01UserDO b01UserDO;

    @ApiModelProperty(value = "订单明细表 length")
    @TableField(exist = false)
    private Integer length;


    @TableField(exist = false)
    @ApiModelProperty(value = "购买数量",name ="num")
    private int num;

    @ApiModelProperty(value = "街道Id",name ="streetId")
    private Long streetId;
}
