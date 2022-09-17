package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * Author tyf
 * Date  2020-04-13
 */
@Data
@ApiModel(value = "APP——购物车查询实体")
public class B04ShoppingCartSearchForm {
    @ApiModelProperty(value = "用户ID",name ="userId")
    @Min(value = 1,message = "用户ID不能为0")
    private long userId;
    @ApiModelProperty(value = "一级分类:国家",name ="categoryId")
    private long categoryId;

}
