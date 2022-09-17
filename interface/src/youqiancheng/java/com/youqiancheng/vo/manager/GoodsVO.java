package com.youqiancheng.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoodsVO {
    @ApiModelProperty("今日新增商品数量")
    private Long goodsForDay;
    @ApiModelProperty("本月新增商品数量")
    private Long goodsForMonth;
    @ApiModelProperty("商品总数量")
    private Long goodsForAll;
}
