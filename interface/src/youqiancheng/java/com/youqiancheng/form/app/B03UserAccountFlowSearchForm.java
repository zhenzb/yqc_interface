package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——账户流水查询实体")
public class B03UserAccountFlowSearchForm {
      @ApiModelProperty(value = "账户Id",name ="accountId")
    private long accountId;
    @ApiModelProperty(value = "流水出入类型:1 红包收入；2购物支出",name ="type")
    private short type;
}
