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
@ApiModel(value = "PC——用户账户查询实体")
public class B02UserAccountSearchForm {
    @ApiModelProperty(value = "用户Id",name ="userId")
    @Min(value = 1,message = "用户ID不能为空")
    private long userId;
}
