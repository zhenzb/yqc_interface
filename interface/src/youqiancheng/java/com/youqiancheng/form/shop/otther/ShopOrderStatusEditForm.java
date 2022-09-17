package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单状态变更表单
 */
@Data
public class ShopOrderStatusEditForm extends ShopCommonForm {
    @ApiModelProperty("订单状态")
    private int orderStatus;
    @ApiModelProperty("发货状态")
    private int deliveryStatus;
}
