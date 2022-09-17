package com.youqiancheng.vo.app;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.B01UserDO;
import com.youqiancheng.mybatis.model.C01GoodsDO;
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
@ApiModel(value = "商城业务订单表实体")
public class D01OrderStatusVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)", name = "id", example = "1", notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单明细状态", name = "orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "商家", name = "shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称", name = "shopName")
    private String shopName;
    @ApiModelProperty(value = "收货人姓名", name = "shippingName")
    private String shippingName;
    @ApiModelProperty(value = "收货人电话", name = "shippingPhone")
    private String shippingPhone;
    @ApiModelProperty(value = "收货人地址", name = "shippingAddress")
    private String shippingAddress;
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
    @ApiModelProperty(value = "购买规格参数",name ="inventory")
    private String inventory;
    @ApiModelProperty(value = "省",name ="province")
    private String province;
    @ApiModelProperty(value = "市",name ="city")
    private String city;
    @ApiModelProperty(value = "区",name ="area")
    private String area;
    @ApiModelProperty(value = "订单明细id",name ="orderDetailsId")
    private long orderItemId;

}



