package com.youqiancheng.form.shop;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "提现申请保存实体")
public class F06WithdrawalApplicationSaveForm   {
    @ApiModelProperty(value = "账户ID",name ="accountId")
    @Min(value = 1,message = "账户ID不能为空")
    private long accountId;
    @ApiModelProperty(value = "提现金额",name ="withdrawalMoney")
    private BigDecimal withdrawalMoney;
    @ApiModelProperty(value = "服务费",name ="serviceMoney")
    private BigDecimal serviceMoney;
    @ApiModelProperty(value = "实际提现金额",name ="actualWithdrawalMoney")
    private BigDecimal actualWithdrawalMoney;
    @ApiModelProperty(value = "原服务费比例",name ="originalServiceRatio")
    private BigDecimal originalServiceRatio;
    @ApiModelProperty(value = "实际服务费比例",name ="actualServiceRatio")
    private BigDecimal actualServiceRatio;
    @ApiModelProperty(value = "提现时本月累计红包金额",name ="currentRedenvelopes")
    private BigDecimal currentRedenvelopes;
    @ApiModelProperty(value = "提现方式",name ="type")
    private int type;
    @ApiModelProperty(value = "提现账户",name ="account")
    //@Min(value = 1,message = "")
    @NotBlank(message = "提现账户不能为空")
    private String account;
}
