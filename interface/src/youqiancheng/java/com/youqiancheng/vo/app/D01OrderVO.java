package com.youqiancheng.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
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
@ApiModel(value = "APP视图——商城业务订单表实体")
public class D01OrderVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
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
    @ApiModelProperty(value = "订单明细",name ="orderItem")
    private List<D02OrderItemDO> orderItem;

    @ApiModelProperty(value = "是否开发票(1,不开票，2开发票)",name ="invoiceFlag")
    private Integer invoiceFlag;
    @ApiModelProperty(value = "发票类型",name ="invoiceType")
    private String invoiceType;
    @ApiModelProperty(value = "发票内容",name ="invoiceContent")
    private String invoiceContent;
    @ApiModelProperty(value = "发票抬头",name ="invoiceTitle")
    private String invoiceTitle;
    @ApiModelProperty(value = "面对面标记",name ="flag")
    private int flag;
}
