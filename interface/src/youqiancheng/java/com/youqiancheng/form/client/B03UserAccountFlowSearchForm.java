package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——账户流水查询实体")
public class B03UserAccountFlowSearchForm {
      @ApiModelProperty(value = "账户Id",name ="accountId")
      @Min(value = 1,message = "账户不能为空")
    private long accountId;
    @ApiModelProperty(value = "流水出入类型:1红包收入；2购物支出;3提现退款收入;9线下消费，线上支付流水",name ="type")
    private short type;



}
