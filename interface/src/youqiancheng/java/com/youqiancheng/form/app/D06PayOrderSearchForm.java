package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author tyf
 * Date  2020-05-12
 */
@Data
@ApiModel(value = "支付订单查询实体")
public class D06PayOrderSearchForm {
    @ApiModelProperty(value = "用户id",name ="userId")
    private Long userId;
    @ApiModelProperty(value = "用户昵称",name ="nick")
    private String nick;
    @ApiModelProperty(value = "用户手机号",name ="userPhone")
    private String userPhone;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
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
    @ApiModelProperty(value = "订单明细",name ="payTime")
    private List<D02OrderItemSaveForm> orderItem;
    @ApiModelProperty(value = "街道Id",name ="streetId")
    private Long streetId;

    private Long shopId;

    private String shopName;

    private Long goodsId;
}
