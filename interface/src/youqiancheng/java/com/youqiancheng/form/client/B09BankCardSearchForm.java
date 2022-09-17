package com.youqiancheng.form.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "PC——银行卡查询实体")
public class B09BankCardSearchForm  {
    @ApiModelProperty(value = "用户id",name ="userId")
    private long userId;
}
