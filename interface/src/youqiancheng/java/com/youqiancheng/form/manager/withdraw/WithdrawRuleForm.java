package com.youqiancheng.form.manager.withdraw;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 商家管理--账户管理--提现列表
 * @Author: Mr.Deng
 * @Date: 2020/4/14 9:20
 * @Version: V1.0
 */
@Data
public class WithdrawRuleForm {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "下限（大于等于）",name ="lowerLimit")
    private BigDecimal lowerLimit;
    @ApiModelProperty(value = "上限（小于）",name ="upperLimit")
    private BigDecimal upperLimit;
    @ApiModelProperty(value = "服务费比例",name ="serviceRatio")
    private BigDecimal serviceRatio;
    @ApiModelProperty("删除标识 1没有删除 2 表示删除")
    private Integer deleteFlag;
}


