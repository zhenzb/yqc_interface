package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--订单管理--订单列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class ShopOrderPageForm {
    @ApiModelProperty("订单号/姓名")
    private String nameOrNo;
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
    @ApiModelProperty("订单状态")
    private Integer orderStatus;
}


