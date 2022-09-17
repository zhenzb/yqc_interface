package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商品汇总条件表单
 * @Author: Mr.Deng
 * @Date: 2020/4/13 14:31
 * @Version: V1.0
 */
@Data
public class ShopGoodsStatisticsForm {
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
}


