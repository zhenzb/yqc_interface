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
public class WithdrawalSetForm {
    @ApiModelProperty(value = "提现方式:手动打款，自动打款",name ="type")
    private Integer type;
    @ApiModelProperty(value = "最低提现金额",name ="lowerLimit")
    private BigDecimal lowerLimit;
    @ApiModelProperty(value = "服务费比例",name ="serviceRatio")
    private BigDecimal serviceRatio;
    @ApiModelProperty(value = "内容",name ="content")
    private String content;
}


