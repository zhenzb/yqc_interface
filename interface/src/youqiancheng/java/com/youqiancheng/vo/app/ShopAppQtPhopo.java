package com.youqiancheng.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "返回二维码图片")
public class ShopAppQtPhopo {

    @ApiModelProperty(value = "支付宝图片",name ="alipayUrl")
    private String alipayUrl;
    @ApiModelProperty(value = "微信图片",name ="wechatUrl")
    private String wechatUrl;
}
