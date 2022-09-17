package com.youqiancheng.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-05-12
 */
@Data
@ApiModel(value = "支付订单实体视图")
public class ALLPayOrderVO  {
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private Long id;
    @ApiModelProperty(value = "订单号",name ="orderNo")
    private String orderNo;
    @ApiModelProperty(value = "订单金额",name ="orderPrice")
    private BigDecimal orderPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单提交时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "订单明细",name ="orderItem")
    private List<AllShopOrderItemVO> orderItem;
    @ApiModelProperty(value = "商品数量",name ="count")
    private int count;

}
