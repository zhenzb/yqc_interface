package com.youqiancheng.vo.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现申请实体")
public class WithdrawalParamVO implements Serializable {
    @ApiModelProperty(value = "最低提现金额",name ="money")
    private BigDecimal money ;
    @ApiModelProperty(value="原服务费比例", name="originalServiceRatio")
    private BigDecimal originalServiceRratio ;
    @ApiModelProperty(value="实际服务费比例", name="actualServiceRratio")
    private BigDecimal actualServiceRratio ;
    @ApiModelProperty(value="本月累积发放红包金额", name="redMoney")
    private BigDecimal redMoney ;
}
