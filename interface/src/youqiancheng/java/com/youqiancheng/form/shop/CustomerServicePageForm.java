package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--订单管理--订单列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class CustomerServicePageForm {
    @ApiModelProperty("售后单号")
    private String serviceNo;
    private long shopId;
}


