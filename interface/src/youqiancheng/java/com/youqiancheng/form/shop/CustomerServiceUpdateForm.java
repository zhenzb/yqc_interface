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
public class CustomerServiceUpdateForm {
    @ApiModelProperty("理由")
    private String refuseReason;
    @ApiModelProperty("订单明细ID")
    private Long  id;
}


