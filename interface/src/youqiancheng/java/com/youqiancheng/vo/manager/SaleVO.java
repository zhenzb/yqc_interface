package com.youqiancheng.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleVO {
    @ApiModelProperty("今日销售额")
    private Long moneyForDay;
    @ApiModelProperty("本周销售额")
    private Long moneyForWeek;
    @ApiModelProperty("本月销售额")
    private Long moneyForMonth;

}
