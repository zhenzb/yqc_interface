package com.youqiancheng.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShopVO {
    @ApiModelProperty("今日新增商家数量")
    private Long shopForDay;
    @ApiModelProperty("本月新增商家数量")
    private Long shopForMonth;
    @ApiModelProperty("商家总数量")
    private Long shopForAll;
}
