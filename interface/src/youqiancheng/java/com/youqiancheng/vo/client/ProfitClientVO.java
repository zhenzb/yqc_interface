/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: A15MessageAppVO
 * Author:   ytf
 * Date:     2020/4/8 17:23
 * Description: 视图——消息实体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.vo.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能：
 * 〈视图——消息实体〉
 *
 * @author ytf
 * @create 2020/4/8
 * @since 1.0.0
 */
@Data
@ApiModel(value = "PC视图——收益实体")
public class ProfitClientVO {
    @ApiModelProperty(value = "总收益",name ="totalProfit")
    private BigDecimal totalProfit;
    @ApiModelProperty(value = "今日收益",name ="dayProfit")
    private BigDecimal dayProfit;
    @ApiModelProperty(value = "昨日收益",name ="yesterdayProfit")
    private BigDecimal yesterdayProfit;
    @ApiModelProperty(value = "余额",name ="balance")
    private BigDecimal balance;
    @ApiModelProperty(value = "可提现过金额",name ="availableWithdrawMoney")
    private BigDecimal availableWithdrawMoney;


}
