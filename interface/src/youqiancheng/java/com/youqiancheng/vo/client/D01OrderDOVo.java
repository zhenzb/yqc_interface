package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class D01OrderDOVo  {
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "商家",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "订单支付状态",name ="orderStatus")
    private int orderStatus;
    @ApiModelProperty(value = "订单价格",name ="orderPrice")
    private BigDecimal orderPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;

    List<D02OrderItemDO> d02OrderItemDOList;
}
