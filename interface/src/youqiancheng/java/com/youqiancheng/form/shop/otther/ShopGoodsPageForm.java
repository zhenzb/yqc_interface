package com.youqiancheng.form.shop.otther;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 商家管理--商品管理
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class ShopGoodsPageForm {
    @ApiModelProperty("商品名称")
    private String goodsName;
    @ApiModelProperty("筛选开始时间")
    private String startTime;
    @ApiModelProperty("筛选截止时间")
    private String endTime;
    @ApiModelProperty("商品审核状态")
    private Integer goodsStatus;
    @ApiModelProperty("类型")
    private Integer type;
    @ApiModelProperty("商家ID")
    private Long shopId;
    @ApiModelProperty("类型2")
    private Integer types;
}


