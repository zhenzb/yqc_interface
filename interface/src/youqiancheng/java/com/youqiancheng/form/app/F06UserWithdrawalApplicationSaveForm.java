package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现申请实体")
public class F06UserWithdrawalApplicationSaveForm {
    @ApiModelProperty(value = "用户ID",name ="id")
    @Min(value = 1,message = "用户ID不能为空")
    private long id;
    @ApiModelProperty(value = "提现金额",name ="withdrawalMoney")
    private BigDecimal withdrawalMoney;

}
