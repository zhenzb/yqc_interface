package com.youqiancheng.vo.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youqiancheng.mybatis.model.D02OrderItemDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "返回二维码图片")
public class ShopQtPhopo {

    @ApiModelProperty(value = "支付宝图片",name ="alipayUrl")
    private String alipayUrl;
    @ApiModelProperty(value = "微信图片",name ="wechatUrl")
    private String wechatUrl;
}
