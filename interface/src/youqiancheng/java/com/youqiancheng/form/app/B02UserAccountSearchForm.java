package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——用户账户查询实体")
public class B02UserAccountSearchForm {
    @ApiModelProperty(value = "用户Id",name ="userId")
    private long userId;
}
