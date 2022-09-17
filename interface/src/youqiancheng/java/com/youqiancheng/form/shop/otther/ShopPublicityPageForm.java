package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--动态(宣传)管理
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class ShopPublicityPageForm {
    @ApiModelProperty("动态标题")
    private String title;
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
    @ApiModelProperty("审核状态")
    private Integer examineStatus;
}


