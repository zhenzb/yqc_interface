package com.youqiancheng.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderVO {
    @ApiModelProperty("今日新增订单数量")
    private Long orderForDay;
    @ApiModelProperty("本周新增订单数量")
    private Long orderForWeek;
    @ApiModelProperty("本月新增订单数量")
    private Long orderForMonth;

}
